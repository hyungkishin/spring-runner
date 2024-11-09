package todoapp.core.user.data.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import todoapp.Constant;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserRepository;

/**
 * Spring Data JPA 기반 사용자 저장소 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Profile(Constant.PROFILE_PRODUCTION)
@Repository
interface JpaUserRepository extends UserRepository, JpaRepository<User, String> {

}
