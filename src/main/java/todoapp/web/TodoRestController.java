package todoapp.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todoapp.core.todo.application.TodoCleanup;
import todoapp.core.todo.application.TodoFind;
import todoapp.core.todo.application.TodoModification;
import todoapp.core.todo.application.TodoRegistry;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoRestController {

    private final TodoFind find;

    private final TodoRegistry registry;

    private final TodoModification modification;

    private final TodoCleanup todoCleanup;

    @GetMapping
    public @ResponseBody Iterable<Todo> readAll() {
        return find.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid final WriteTodoCommand command) {
        log.debug("request payload: {} ", command);
        registry.register(command.text);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id, @RequestBody @Valid final WriteTodoCommand command) {
        log.debug("request id: {}, command: {}", id, command);
        modification.modify(TodoId.of(id), command.text(), command.completed());
    }

    record WriteTodoCommand(@NotBlank @Size(min = 4, max = 140) String text, boolean completed) {
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        todoCleanup.clear(TodoId.of(id));
    }

}
