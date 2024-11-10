package todoapp.security.web.servlet;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import todoapp.security.AccessDeniedException;
import todoapp.security.UnauthorizedAccessException;
import todoapp.security.support.RolesAllowedSupport;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {

    // private final UserSessionHolder userSessionHolder; // HttpServletRequest 내부에 강력한 기능으로 없어질 수 있다.
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        request.getUserPrincipal(); // 이 인증정보 는 뭔가요 ?
//        request.isUserInRole(); // 이 유저 인가된 사용자가 맞나요 ?
        if (handler instanceof HandlerMethod handlerMethod) {
            // 로그인이 되어있는가 ?
            var roleAllowed = handlerMethod.getMethodAnnotation(RolesAllowed.class);

            // handler 가 없으면 controller 에 붙어있는 어노테이션을 보겠다.
            if (Objects.isNull(roleAllowed)) {
                roleAllowed = AnnotatedElementUtils.findMergedAnnotation(
                        handlerMethod.getBeanType(),
                        RolesAllowed.class
                );
            }

            if (Objects.nonNull(roleAllowed)) {
                log.debug("verify roles-allowed: {}", roleAllowed);

                // 1. 로그인이 되어있나요 ?
                if (Objects.isNull(request.getUserPrincipal())) {
                    throw new UnauthorizedAccessException();
                }

                // 2. 역할은 적절한가요 ?
                var matchedRoles = Stream.of(roleAllowed.value())
                        .filter(request::isUserInRole)
                        .collect(Collectors.toSet());

                log.debug("matched roles: {}", matchedRoles);

                if (matchedRoles.isEmpty()) {
                    throw new AccessDeniedException();
                }

            }
        }

        return true;
    }

}
