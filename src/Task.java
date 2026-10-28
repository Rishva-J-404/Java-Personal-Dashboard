/**
 * Task.java
 * Represents a single daily task with name, priority, and completion status.
 * Demonstrates: OOP Encapsulation with private fields and public getters/setters.
 */
public class Task {
    // Private variables (Encapsulation)
    private String name;
    private String priority;
    private boolean completed;

    // Constructor for creating a new pending task
    public Task(String name, String priority) {
        this.name = name;
        this.priority = priority;
        this.completed = false; // A newly created task is always Pending
    }

    // Overloaded constructor for loading saved tasks from file
    public Task(String name, String priority, boolean completed) {
        this.name = name;
        this.priority = priority;
        this.completed = completed;
    }

    // Getter for task name
    public String getName() {
        return name;
    }

    // Getter for priority (High, Medium, Low)
    public String getPriority() {
        return priority;
    }

    // Getter for completion status
    public boolean isCompleted() {
        return completed;
    }

    // Mark task as completed
    public void markCompleted() {
        this.completed = true;
    }

    // Setter for completion status (useful for toggling)
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // String representation of task
    @Override
    public String toString() {
        String status = completed ? "Completed" : "Pending";
        return name + " [" + priority + "] - " + status;
    }
}
