package todoapp.data;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import todoapp.core.todo.domain.Todo;
import todoapp.core.todo.domain.TodoIdGenerator;
import todoapp.core.todo.domain.TodoRepository;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "todoapp.data.init", havingValue = "true")
public class ApplicationDataInit implements InitializingBean, ApplicationRunner, CommandLineRunner {

    private final TodoIdGenerator todoIdGenerator;

    private final TodoRepository todoRepository;

    // InitializingBean
    @Override
    public void afterPropertiesSet() throws Exception {
        todoRepository.save(Todo.create("Task one", todoIdGenerator));
    }

    // ApplicationRunner
    @Override
    public void run(ApplicationArguments args) throws Exception {
        todoRepository.save(Todo.create("Task two", todoIdGenerator));
    }

    // CommandLineRunner
    @Override
    public void run(String... args) throws Exception {
        todoRepository.save(Todo.create("Task three", todoIdGenerator));
    }
}
