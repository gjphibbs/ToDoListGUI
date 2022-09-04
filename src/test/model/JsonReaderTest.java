package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Code adapted from JsonReaderTest in JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList tdl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyToDoList.json");
        try {
            ToDoList tdl = reader.read();
            assertEquals(0, tdl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralToDoList.json");
        try {
            ToDoList tdl = reader.read();
            List<Task> tasks = tdl.getTasks();
            assertEquals(2, tasks.size());
            checkTask("task 1", "description 1", false, tasks.get(0));
            checkTask("task 2", "description 2", false, tasks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
