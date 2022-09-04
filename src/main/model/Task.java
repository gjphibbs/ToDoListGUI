package model;

import org.json.JSONObject;
import persistence.Writable;

public class Task implements Writable {
    private String name;
    private String description;
    private boolean completion;

    //EFFECTS: creates a new Task with given name and description. Sets completion status to false.
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.completion = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompletion() {
        return completion;
    }

    //MODIFIES: this
    //EFFECTS: sets completion status to given boolean.
    public void setCompletion(boolean comp) {
        this.completion = comp;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("completion", completion);
        return json;
    }


    //EFFECTS: Converts a task to a string format.
    @Override
    public String toString() {
        if (description.isEmpty()) {
            return ("Task: " + name);
        } else {
            return ("Task: " + name + "  " + "Description: " + description);
        }
    }
}
