package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.web.model.SiteProperties;

import java.util.Objects;

@Controller
public class TodoController {

    private final SiteProperties siteProperties;

    public TodoController(SiteProperties siteProperties) {
        this.siteProperties = Objects.requireNonNull(siteProperties);
    }

    @ModelAttribute("site") // 이게 왜 동작한다고 했지 ? : handler 후 처리
    private SiteProperties siteProperties() {
        return siteProperties;
    }

    @RequestMapping("/todos")
    public void todos(Model model) {
    }
}
