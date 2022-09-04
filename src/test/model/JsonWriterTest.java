package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList tdl = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyToDoList() {
        try {
            ToDoList tdl = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            tdl = reader.read();
            assertEquals(0, tdl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralToDoList() {
        try {
            ToDoList tdl = new ToDoList();
            tdl.addTask(new Task("Task 1", "description 1"));
            tdl.addTask(new Task("Task 2", "description 2"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            tdl = reader.read();
            List<Task> tasks = tdl.getTasks();
            assertEquals(2, tasks.size());
            checkTask("Task 1", "description 1", false, tasks.get(0));
            checkTask("Task 2", "description 2", false, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}