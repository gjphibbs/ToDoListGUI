package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import exceptions.NameException;
import model.ToDoList;
import model.Task;
import persistence.JsonReader;
import persistence.JsonWriter;

//Class adapted from JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// and from TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp

//To-Do List application
public class ToDoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private ToDoList mainList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the teller application
    public ToDoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        mainList = new ToDoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDoList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runToDoList() {
        boolean keepGoing = true;
        String command = null;

        init();
        startLoad();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                remindSave();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            taskAdd();
        } else if (command.equals("r")) {
            taskRemove();
        } else if (command.equals("p")) {
            taskCounter();
        } else if (command.equals("c")) {
            completeTask();
        } else if (command.equals("d")) {
            displayTasks();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes mainList and input
    private void init() {
        mainList = new ToDoList();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add task");
        System.out.println("\tr -> remove task");
        System.out.println("\td -> display tasks");
        System.out.println("\tc -> mark as complete");
        System.out.println("\tp -> amount of completed tasks");
        System.out.println("\ts -> save todolist to file");
        System.out.println("\tl -> load todolist from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: Adds a task to the ToDoList
    private void taskAdd() {
        System.out.println("Enter task name:");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Enter task description:");
        String description = input.nextLine();

        Task newTask = new Task(name, description);
        mainList.addTask(newTask);
    }

    // MODIFIES: this
    // EFFECTS: Removes a task with given name from the ToDoList. If no task with given name in todolist, returns error.
    private void taskRemove() {
        System.out.println("Enter task name to remove:");
        input.nextLine();
        String name = input.nextLine();
        try {
            mainList.removeTaskByName(name);
        } catch (NameException e) {
            System.out.println("There is no task with that name!");
        }
    }

    // EFFECTS: shows the user the number of Incomplete and Complete tasks
    private void taskCounter() {
        int[] ans = mainList.taskCounter();
        String ans1 = ("Incomplete Tasks: " + ans[0] + ", Complete Tasks: " + (ans[1]));

        System.out.println(ans1);
    }

    // MODIFIES: this
    // EFFECTS: sets a task with given name to Complete. If given name is not in list returns error.
    private void completeTask() {
        System.out.println("Enter task name to complete:");
        input.nextLine();
        String name = input.nextLine();
        try {
            mainList.completeTask(name);
        } catch (NameException e) {
            System.out.println("There is no task with that name!");
        }
    }


    // EFFECTS: displays a list of all tasks with names and descriptions to user. Any complete tasks are indicated.
    private void displayTasks() {
        for (int i = 0; i < mainList.getSize(); i++) {
            Task cur = mainList.getPlace(i);
            if (cur.getCompletion()) {
                System.out.println("Name: " + cur.getName() + " " + "Description: " + cur.getDescription());
                System.out.println("Task Complete!");
                System.out.println("");
            } else {
                System.out.println("Name: " + cur.getName() + " " + "Description: " + cur.getDescription());
                System.out.println("");
            }
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(mainList);
            jsonWriter.close();
            System.out.println("Saved ToDoList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadToDoList() {
        try {
            mainList = jsonReader.read();
            System.out.println("Loaded ToDoList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prompts user to save to do list
    private void remindSave() {
        System.out.println("Would you like to save?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        String decision = input.next();
        if (decision.equals("y")) {
            saveToDoList();
        } else if (!decision.equals("n")) {
            System.out.println("Selection not valid...");
            remindSave();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to load to do list
    private void startLoad() {
        System.out.println("Would you like to load your ToDoList from file?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        String decision = input.next();
        if (decision.equals("y")) {
            loadToDoList();
        } else if (!decision.equals("n")) {
            System.out.println("Selection not valid...");
            startLoad();
        }
    }
}
