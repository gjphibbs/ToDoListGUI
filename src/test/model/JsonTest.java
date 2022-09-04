package model;

import model.ToDoList;
import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Class adapted from JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkTask(String name, String description, Boolean completion, Task task) {
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(completion, task.getCompletion());
    }

}
