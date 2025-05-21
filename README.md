# ToDoList Swing Interface
A simple desktop to-do list interface built with Java Swing. You can add tasks with a priority, optional deadline, and notes—and mark them done or delete them.

**Features**

- **Add New Task:** Enter a name, priority (positive integer), optional deadline (year/month/day), and notes.

- **Mark Complete:** Toggle tasks as done; completed items move to the bottom and get a strikethrough.

- **Delete Task:** Remove items you no longer need.

- **Sort by Priority:** Tasks are automatically sorted from lowest to highest priority.

- **Save & UI:** Simple “Save Item” and “New Item” buttons, plus a menu bar with Exit.

**Requirements**

- Java 8 or higher

- No external libraries (all Swing, Java standard)


**Code Structure**

- **ToDos.java:** Main frame, UI setup, listeners, date logic.

- **Item.java:** Task model (name, priority, deadline, notes, completed flag). Implements comparable for sorting.


**How to Use**

**1.** Click New Item to clear fields.

**2.** Enter Item name and Priority.

**3.** Check Deadline and pick a date. (Optional) 

**4.** Add any Notes.

**5.** Click Save Item (it appears in the list).

**6.** Select a task and click Toggle Done or Delete.



**Preview:**

