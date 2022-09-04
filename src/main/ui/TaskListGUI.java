package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;


//Some code loosely adapted from ListDemo by oracle
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
//Represents a graphical TaskListUI
public class TaskListGUI extends JPanel implements ListSelectionListener {
    private JList<String> toDoListGUI;
    private ToDoListModel listModel;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/todolist.json";

    private static final String addString = "Add Task";
    private static final String removeString = "Remove Task";
    private static final String saveString = "Save ToDoList";
    private static final String loadString = "Load ToDoList";

    //Buttons
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;

    //Labels
    private JLabel nameLabel;
    private JLabel descriptionLabel;

    //Text Fields
    private JTextField taskName;
    private JTextField taskDescription;

    //Main scroll pane
    private JScrollPane listScrollPane;

    //EFFECTS: Creates a new TaskList GUI
    public TaskListGUI() {
        super(new BorderLayout());

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        listModel = new ToDoListModel(new ToDoList());
        toDoListGUI = new JList(listModel);
        toDoListGUI.setSelectedIndex(0);
        toDoListGUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoListGUI.addListSelectionListener(this);
        toDoListGUI.setVisibleRowCount(10);
        listScrollPane = new JScrollPane(toDoListGUI);

        buttonMaker();
        createPanel();
    }

    //MODIFIES: this
    //EFFECTS: Makes all buttons for TaskList GUI
    public void buttonMaker() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setEnabled(false);

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());

        taskName = new JTextField(20);
        nameLabel = new JLabel("Name");
        taskName.addActionListener(addListener);
        taskName.getDocument().addDocumentListener(addListener);

        taskDescription = new JTextField(50);
        descriptionLabel = new JLabel("Description");
        taskDescription.addActionListener(addListener);
        taskDescription.getDocument().addDocumentListener(addListener);
    }

    //MODIFIES: this
    //EFFECTS: Creates the panel for the taskList GUI
    public void createPanel() {
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(nameLabel);
        buttonPane.add(taskName);
        buttonPane.add(descriptionLabel);
        buttonPane.add(taskDescription);
        buttonPane.add(addButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }


    //Represents a Listener which listens for actions relating to the Save button
    class RemoveListener implements ActionListener {

        //MODIFIES: TaskListGUI
        //EFFECTS: Removes a task from the listModel when the remove task button is pressed
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            int index = toDoListGUI.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //No tasks left, disable removing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                toDoListGUI.setSelectedIndex(index);
                toDoListGUI.ensureIndexIsVisible(index);
            }
        }
    }

    //Represents a Listener which listens for actions relating to the Save button
    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        //MODIFIES: TaskListGUI
        //EFFECTS: Adds a task to the listModel when the task name text box is filled and the add task button is pressed
        public void actionPerformed(ActionEvent e) {
            String task = taskName.getText();
            String description = taskDescription.getText();

            int index = toDoListGUI.getSelectedIndex(); //get selected index

            Task taskToAdd = new Task(task, description);
            listModel.addTask(taskToAdd);

            //Reset the text field.
            taskName.requestFocusInWindow();
            taskName.setText("");
            taskDescription.requestFocusInWindow();
            taskDescription.setText("");

            //Select the new item and make it visible.

            removeButton.setEnabled(true);
            toDoListGUI.setSelectedIndex(0);
            toDoListGUI.ensureIndexIsVisible(index);
        }

        //MODIFIES: TaskListGUI
        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //MODIFIES: TaskListGUI
        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //MODIFIES: TaskListGUI
        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //MODIFIES: TaskListGUI
        //EFFECTS: Enable the AddTask button if it is not already enabled and if the name text field is !empty.
        private void enableButton() {
            if (!alreadyEnabled && !taskName.getText().isEmpty()) {
                button.setEnabled(true);
            }
        }

        //MODIFIES: TaskListGUI
        //EFFECTS: If the text box is empty returns true and disables the addTask button, otherwise returns false.
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0 && taskName.getText().isEmpty()) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    //Represents a Listener which listens for actions relating to the Save button
    class SaveListener implements ActionListener {

        //MODIFIES: todolist.json
        //EFFECTS: Saves the listModel when the Save button is pressed
        public void actionPerformed(ActionEvent e) {
            final Runnable runnable =
                    (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.close");

            try {
                jsonWriter.open();
                jsonWriter.write(listModel.getList());
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved ToDoList to " + JSON_STORE);
                if (runnable != null) {
                    runnable.run();
                }

            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //Represents a Listener which listens for actions relating to the Load button
    class LoadListener implements ActionListener {

        //MODIFIES: todolist.json
        //EFFECTS: Loads the listModel when the Load button is pressed
        public void actionPerformed(ActionEvent e) {
            try {
                ToDoList loading = jsonReader.read();
                if (loading.getSize() <= 0) {
                    removeButton.setEnabled(false);
                } else {
                    removeButton.setEnabled(true);
                }
                listModel.overwrite(loading);
                Toolkit.getDefaultToolkit().beep();
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }


    //EFFECTS: Disables the removeButton if no selection is made and the list selection is not adjusting.
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (toDoListGUI.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }
}
