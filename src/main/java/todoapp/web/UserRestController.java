package todoapp.web;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;
import todoapp.security.UserSessionHolder;
import todoapp.web.model.UserProfile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RolesAllowed(UserSession.ROLE_USER)
@RequestMapping("/api/user")
public class UserRestController {

    private final ProfilePictureStorage profilePictureStorage;

    private final ProfilePictureChanger profilePictureChanger;

    private final UserSessionHolder userSessionHolder;

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

    @PostMapping("/profile-picture")
    public UserProfile changeProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture, UserSession session) {
        log.debug("profilePicture: {}, {}", profilePicture.getOriginalFilename(), profilePicture.getContentType());
        // 업로드된 프로필 이미지 파일 저장하기
        var profilePictureUri = profilePictureStorage.save(profilePicture.getResource());
        // 프로필 이미지 변경 후 세션 갱신하기
        var updatedUser = profilePictureChanger.change(session.getName(), new ProfilePicture(profilePictureUri));
        userSessionHolder.set(new UserSession(updatedUser));
        return new UserProfile(updatedUser);
    }

}
