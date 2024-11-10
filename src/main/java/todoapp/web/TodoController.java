package todoapp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.todo.application.TodoFind;
import todoapp.web.convert.TodoToSpreadsheetConverter;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoFind find;

    @RequestMapping("/todos")
    public void todos(Model model) {

    }

    @RequestMapping(path = "/todos", produces = "text/csv")
    public void downloadTodos(Model model) {
        model.addAttribute(new TodoToSpreadsheetConverter().convert(find.all()));
    }

}
