package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task testTask = new Task("", "");
    private Task testTask2 = new Task("task 1", "description");

    @Test
    public void testGetters() {
        assertFalse(testTask.getCompletion());
        assertEquals(testTask.getName(), "");
        assertEquals(testTask.getDescription(), "");
    }

    @Test
    public void testSetCompletion() {
        assertFalse(testTask.getCompletion());
        testTask.setCompletion(true);
        assertTrue(testTask.getCompletion());
    }

    @Test
    public void testToString() {
        assertEquals(testTask.toString(), "Task: ");
        assertEquals(testTask2.toString(), "Task: task 1  Description: description");
    }
}
