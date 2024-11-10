package todoapp.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.application.UserRegistration;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserPasswordVerifier userPasswordVerifier;

    private final UserRegistration userRegistration;

    private final UserSessionHolder userSessionHolder;

    @RequestMapping("/login")
    public void loginForm(Model model) {

    }

    @PostMapping("/login")
    public String loginProcess(
            @Valid final LoginCommand request,
            BindingResult bindingResult,
            Model model
    ) {
        log.debug("bindingResult: {}", bindingResult);

        // 0. 입력 값 점증에 실패한 경우: login 페이지로 돌려보내고, 오류 메시지 노출하기
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("message", "입력 값에 문제가 있어요");
            return "login";
        }

        User user = null;
        try {
            user = userPasswordVerifier.verify(request.username, request.password);
        } catch (UserEntityNotFoundException e) {
            user = userRegistration.join(request.username(), request.password());
        } catch (UserPasswordNotMatchedException e) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("message", "비밀번호 틀렸습니다.");
            return "login";
        }

        userSessionHolder.set(new UserSession(user));

        return "redirect:/todos";
    }

    /* 나의 경우에는 한눈에 보이는게 좋을것 같아서, 따로 두진 않고싶어.
    @ExceptionHandler(UserPasswordNotMatchedException.class)
        public String handleUserPasswordInvalid(UserPasswordNotMatchedException e ,Model model) {
            model.addAttribute("message", e.getMessage());
            return "login";
    }
    */

    record LoginCommand(@Size(min = 4, max = 20) String username, String password) {
    }

}
