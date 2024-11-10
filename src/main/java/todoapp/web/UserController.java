package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ProfilePictureStorage profilePictureStorage;

    @RequestMapping("/user/profile-picture")
    @RolesAllowed(UserSession.ROLE_USER)
    public ProfilePicture profilePicture(UserSession session) {
        return session.getUser().getProfilePicture();
    }

}
