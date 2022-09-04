package model;

import exceptions.NameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private Task testTask;
    private Task testTask2;
    private ToDoList testList;

    @BeforeEach
    public void setup() {
        testList = new ToDoList();
        testTask = new Task("", "");
        testTask2 = new Task("2", "2");
    }


    @Test
    public void testGetPlace() {
        testList.addTask(testTask2);
        assertEquals(testTask2, testList.getPlace(0));
    }

    @Test
    public void testAddTask() {
        testList.addTask(testTask);
        assertEquals(testList.getSize(), 1);
    }

    @Test
    public void testAddMultipleTask() {
        testList.addTask(testTask);
        testList.addTask(testTask2);
        assertEquals(testList.getSize(), 2);
    }

    @Test
    public void testRemoveTaskNoException() {
        testList.addTask(testTask);
        try {
            testList.removeTaskByName("");
            assertEquals(testList.getSize(), 0);
        } catch (NameException e) {
            fail("Should not have thrown name exception");
        }
    }

    @Test
    public void testRemoveTaskException() {
        testList.addTask(testTask);
        try {
            testList.removeTaskByName("123");
            fail("Should have thrown exception");
        } catch (NameException e) {
            //pass
        }
    }

    @Test
    public void testRemoveMultipleTaskNoException() {
        testList.addTask(testTask);
        testList.addTask(testTask2);
        try {
            testList.removeTaskByName("");
            testList.removeTaskByName("2");
            assertEquals(testList.getSize(), 0);
        } catch (NameException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    public void testRemoveMultipleTaskException() {
        testList.addTask(testTask);
        testList.addTask(testTask2);
        try {
            testList.removeTaskByName("");
            testList.removeTaskByName("3");
            fail("Should have thrown exception");
        } catch (NameException e) {
            //pass
        }
    }

    @Test
    public void testRemove() {
        testList.addTask(testTask);
        testList.remove(0);
        assertEquals(testList.getSize(), 0);
    }

    @Test
    public void testClear() {
        testList.addTask(testTask);
        testList.addTask(testTask2);
        testList.clear();
        assertEquals(testList.getSize(), 0);
    }

    @Test
    public void testCompleteTaskNoException() {
        testList.addTask(testTask);
        testList.addTask(testTask2);

        try {
            testList.completeTask("");
            Task tester = testList.getPlace(0);
            assertTrue(tester.getCompletion());
        } catch (NameException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    public void testCompleteTaskException() {
        testList.addTask(testTask);
        testList.addTask(testTask2);

        try {
            testList.completeTask("123");
            fail("Should have thrown exception");
        } catch (NameException e) {
            //pass
        }
    }

    @Test
    public void testTaskCounter() {
        testList.addTask(testTask);
        testList.addTask(testTask2);

        int[] counted = testList.taskCounter();
        assertEquals(2, counted[0]);
        assertEquals(0, counted[1]);

        try {
            testList.completeTask("");
        } catch (NameException e) {
            fail("Should not have thrown exception");
        }

        int[] counted2 = testList.taskCounter();
        assertEquals(1, counted2[0]);
        assertEquals(1, counted2[1]);

    }
}
