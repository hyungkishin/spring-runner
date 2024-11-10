package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

@RequiredArgsConstructor
@RestController
@RolesAllowed(UserSession.ROLE_USER)
@RequestMapping("/api/user")
public class UserRestController {

    /* @RolesAllowed(UserSession.ROLE_USER)
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> userProfile(UserSession userSession) {
        if (Objects.nonNull(userSession)) {
            return ResponseEntity.ok(new UserProfile(userSession.getUser()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build();
    } */

    @GetMapping("/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

}
