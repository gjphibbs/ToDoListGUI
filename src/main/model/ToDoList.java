package model;

import exceptions.NameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoList implements Writable {
    private List<Task> tasks;

    //EFFECTS: creates a new ToDoList
    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public int getSize() {
        return tasks.size();
    }

    //REQUIRES: ind be an integer within the size of tdList
    //EFFECTS: returns object at index number ind
    public Task getPlace(int ind) {
        return tasks.get(ind);
    }


    //MODIFIES: this
    //EFFECTS: adds the given task to the ToDoList
    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    //MODIFIES: this
    //EFFECTS: removes the given task from the ToDoList
    public void removeTaskByName(String taskName) throws NameException {
        Boolean container = false;
        for (Task cur : tasks) {
            if (cur.getName().equals(taskName)) {
                container = true;
                break;
            }
        }

        if (container) {
            tasks.removeIf(cur -> cur.getName().equals(taskName));
        } else {
            throw new NameException();
        }
    }

    //EFFECTS: removes the task at given index
    public void remove(int ind) {
        tasks.remove(ind);
    }

    // EFFECTS: returns an unmodifiable list of tasks in this todolist
    public List<Task> getTasks() {
        return (Collections.unmodifiableList(tasks));
    }

    //MODIFIES: this
    //EFFECTS: Clear the task list
    public void clear() {
        tasks.clear();
    }

    //MODIFIES: this
    //EFFECTS: Sets any task with given taskName in tdList to have a completion value of true.
    public void completeTask(String taskName) throws NameException {
        boolean container = false;
        int i = 0;
        for (Task cur : tasks) {
            if (cur.getName().equals(taskName)) {
                cur.setCompletion(true);
                tasks.set(i, cur);
                container = true;
            }
            i++;
        }
        if (!container) {
            throw new NameException();
        }
    }


    //EFFECTS: returns a list of two integers where the first integer is the number of incomplete tasks in the list
    // and the second is the number of complete tasks.
    public int[] taskCounter() {
        int compCount = 0;
        int incompCount = 0;

        for (Task cur : tasks) {
            if (cur.getCompletion()) {
                compCount++;
            } else {
                incompCount++;
            }
        }
        return new int[]{incompCount, compCount};
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns tasks in this todolist as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : tasks) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
