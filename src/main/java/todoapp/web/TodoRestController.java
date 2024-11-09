package todoapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.domain.Todo;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TodoRestController {

    private final TodoFind find;

    public TodoRestController(TodoFind find) {
        this.find = Objects.requireNonNull(find);
    }

    @GetMapping("/todos")
    public @ResponseBody Iterable<Todo> readAll() {
        return find.all();
    }

}
