package ui;

import javax.swing.*;

//Represents a ToDoListGUI
public class ToDoListGUI extends JFrame {
    private TaskListGUI newContentPane;

    //Some code loosely adapted from ListDemo by oracle
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
    //Creates a new ToDoListGUI and show it
    public ToDoListGUI() {
        //Create and set up the window.
        super("ToDoList");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        newContentPane = new TaskListGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        setContentPane(newContentPane);

        //Display the window.
        pack();
        setVisible(true);
    }
}
