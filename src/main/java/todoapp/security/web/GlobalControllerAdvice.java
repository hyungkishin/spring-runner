package todoapp.security.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import todoapp.web.model.SiteProperties;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {

    private final SiteProperties siteProperties;

    @ModelAttribute("site")
    public SiteProperties siteProperties() {
        return siteProperties;
    }

}
