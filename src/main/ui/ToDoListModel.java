package ui;

import model.Task;
import model.ToDoList;

import javax.swing.*;

//Represents a ToDoList listModel for the swing widget toolkit
public class ToDoListModel extends AbstractListModel<Task> {
    private ToDoList list;

    //Creates a new ToDoListModel
    public ToDoListModel(ToDoList list) {
        this.list = list;
    }

    public ToDoList getList() {
        return list;
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    //EFFECTS: gets element at given index
    @Override
    public Task getElementAt(int ind) {
        return list.getPlace(ind);
    }

    //MODIFIES: this
    //EFFECTS: adds a task to the models ToDoList
    public void addTask(Task t) {
        list.addTask(t);
        this.fireContentsChanged(this, list.getSize() - 1, list.getSize() - 1);
    }

    //MODIFIES: this
    //EFFECTS: removes a task from the models ToDoList
    public void remove(int ind) {
        list.remove(ind);
        this.fireContentsChanged(this, ind, ind);
    }

    //MODIFIES: this
    //EFFECTS: removes all tasks from the models ToDoList
    public void clearList() {
        list.clear();
        this.fireContentsChanged(this, 0, list.getSize() - 1);
    }

    //MODIFIES: this
    //EFFECTS: removes all tasks from the models ToDoList and then adds all tasks from given ToDoList
    public void overwrite(ToDoList tdl) {
        clearList();
        list = tdl;
        this.fireContentsChanged(this, 0, list.getSize() - 1);
    }
}
