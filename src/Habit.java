/**
 * Habit.java
 * Represents a daily habit and tracks whether it has been completed.
 * Demonstrates: OOP Encapsulation and clean status tracking.
 */
public class Habit {
    // Private variables (Encapsulation)
    private String name;
    private boolean completed;

    // Constructor for creating a new habit (starts pending/not completed)
    public Habit(String name) {
        this.name = name;
        this.completed = false;
    }

    // Overloaded constructor for loading saved habits from file
    public Habit(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    // Getter for habit name
    public String getName() {
        return name;
    }

    // Check if habit is completed
    public boolean isCompleted() {
        return completed;
    }

    // Mark habit as completed
    public void markCompleted() {
        this.completed = true;
    }

    // Setter for completion status (to toggle between completed and pending)
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // String representation of habit
    @Override
    public String toString() {
        String status = completed ? "Completed" : "Pending";
        return name + " - " + status;
    }
}
