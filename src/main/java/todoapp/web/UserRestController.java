package todoapp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;
import todoapp.web.model.UserProfile;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserSessionHolder userSessionHolder;

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> userProfile() {
        UserSession userSession = userSessionHolder.get();
        if (Objects.nonNull(userSession)) {
            return ResponseEntity.ok(new UserProfile(userSession.getUser()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build();
    }

}
