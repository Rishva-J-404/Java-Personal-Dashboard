/**
 * Goal.java
 * Represents a long-term goal with a target value and tracked progress.
 * Demonstrates: OOP Encapsulation and business logic validation.
 */
public class Goal {
    // Private variables (Encapsulation)
    private String name;
    private int target;
    private int progress;

    // Constructor for creating a new goal (progress starts at 0)
    public Goal(String name, int target) {
        this.name = name;
        this.target = target;
        this.progress = 0;
    }

    // Overloaded constructor for loading saved goals from file
    public Goal(String name, int target, int progress) {
        this.name = name;
        this.target = target;
        updateProgress(progress); // Enforce target validation
    }

    // Getter for goal name
    public String getName() {
        return name;
    }

    // Getter for target
    public int getTarget() {
        return target;
    }

    // Getter for current progress
    public int getProgress() {
        return progress;
    }

    // Update progress — does not allow progress to exceed the target or fall below 0
    public void updateProgress(int newProgress) {
        if (newProgress < 0) {
            this.progress = 0;
        } else if (newProgress > target) {
            this.progress = target; // Progress cannot exceed the target
        } else {
            this.progress = newProgress;
        }
    }

    // Calculate percentage of goal achieved (0 to 100%)
    public int getPercentage() {
        if (target <= 0) {
            return 0;
        }
        int percentage = (int) Math.round(((double) progress / target) * 100.0);
        if (percentage > 100) {
            return 100;
        }
        return percentage;
    }

    // String representation of goal
    @Override
    public String toString() {
        return name + " - " + progress + "/" + target + " (" + getPercentage() + "%)";
    }
}
