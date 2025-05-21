import java.time.LocalDate;

public class Item implements Comparable<Item> {
    private String name;
    private int priority;
    LocalDate deadline;
    private String notes;
    private boolean completed;

    public Item(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() { 
        return name;
    }

    public int getPriority() {
        return priority;
    }
    
    public Object getDeadLine()
    {
    	return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int compareTo(Item other) {
        return Integer.compare(this.priority, other.priority);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
