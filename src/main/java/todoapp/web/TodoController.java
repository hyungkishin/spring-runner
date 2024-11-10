package todoapp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.TodoFind;
import todoapp.web.convert.TodoToSpreadsheetConverter;
import todoapp.web.model.SiteProperties;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final SiteProperties siteProperties;

    private final TodoFind find;

    @ModelAttribute("site") // handler 후 처리
    private SiteProperties siteProperties() {
        return siteProperties;
    }

    @RequestMapping("/todos")
    public void todos(Model model) {

    }

    @RequestMapping(path = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute(new TodoToSpreadsheetConverter().convert(find.all()));
    }

}
