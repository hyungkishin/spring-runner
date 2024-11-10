package todoapp.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 사이트 정보 모델
 *
 * @author springrunner.kr@gmail.com
 */
@Getter
@Setter
@ConfigurationProperties("todoapp.site")
public class SiteProperties {

    private String author = "unknown";

    private String description = "TodoApp templates for Server-side";

}
