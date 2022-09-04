# My Personal Project

## Calendar

I plan to design a **to-do application** which allows a user to: 

- create a to-do list
- cross items off as they complete them
 
I plan on using this application to help myself organize day-to-day tasks. I am interested in this project as I
struggle with personal organization and hope investing time in a tool like this could help.
 **User Stories**
 
- As a user, I want to be able to add a task to my to-do list
- As a user, I want to be able to view the list of tasks on my to-do list
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to delete a task from my to-do-list
- As a user, I want to be able to see the number of incomplete and number of completed tasks on my to-do list
- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to be able to load my to-do list from file
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list to
  file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my to-do list from file.

**Phase 4: Task 2**

I implemented a robust class in my model package. The ToDoList class is robust. Within this class the removeTaskByName 
method and the completeTask method are robust.

**Phase 4: Task 3**

- Make the relationship between Task and ToDoList bidirectional so that a Task knows which ToDoList it is a part of
in the case of multiple ToDoLists.
- Remove ToDoListModel's association with ToDoList and instead make it have a field of list of Tasks so that it better
aligns with the ToDoList class.
- I think that as the project increased in size, I would want to move some of the listener classes out of 
the TaskListGUI class and into their own individual classes.