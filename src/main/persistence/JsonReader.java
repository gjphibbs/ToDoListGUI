package persistence;

import model.Task;
import model.ToDoList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


//Class adapted from JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList tdl = new ToDoList();
        addTasks(tdl, jsonObject);
        return tdl;
    }

    // MODIFIES: tdl
    // EFFECTS: parses Tasks from JSON object and adds them to tdl
    private void addTasks(ToDoList tdl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tdl, nextTask);
        }
    }

    // MODIFIES: tdl
    // EFFECTS: parses Task from JSON object and adds it to tdl
    private void addTask(ToDoList tdl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        boolean completion = jsonObject.getBoolean("completion");
        Task task = new Task(name, description);
        task.setCompletion(completion);
        tdl.addTask(task);
    }

}
