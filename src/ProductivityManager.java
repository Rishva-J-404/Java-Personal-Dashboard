import java.util.ArrayList;

/**
 * ProductivityManager.java
 * Central controller class managing collections of Tasks, Goals, Habits, and Expenses.
 * Demonstrates: Java Collections (ArrayList) and simple algorithmic processing with basic loops.
 */
public class ProductivityManager {
    // Collections to store objects in memory
    private ArrayList<Task> tasks;
    private ArrayList<Goal> goals;
    private ArrayList<Habit> habits;
    private ArrayList<Expense> expenses;

    // Constructor initializing empty collections
    public ProductivityManager() {
        this.tasks = new ArrayList<Task>();
        this.goals = new ArrayList<Goal>();
        this.habits = new ArrayList<Habit>();
        this.expenses = new ArrayList<Expense>();
    }

    // ==========================================
    // Task Operations
    // ==========================================

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // Count completed tasks using a simple loop
    public int getCompletedTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    // ==========================================
    // Goal Operations
    // ==========================================

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public void removeGoal(int index) {
        if (index >= 0 && index < goals.size()) {
            goals.remove(index);
        }
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    // ==========================================
    // Habit Operations
    // ==========================================

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void removeHabit(int index) {
        if (index >= 0 && index < habits.size()) {
            habits.remove(index);
        }
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    // Count completed habits using a simple loop
    public int getCompletedHabits() {
        int count = 0;
        for (Habit habit : habits) {
            if (habit.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    // ==========================================
    // Expense Operations
    // ==========================================

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
        }
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    // Calculate total expense amount using a simple loop
    public double getTotalExpenses() {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    // ==========================================
    // Progress Calculations (Section 17)
    // ==========================================

    // Task progress: Completed Tasks / Total Tasks * 100
    public int getTaskProgressPercentage() {
        if (tasks.isEmpty()) {
            return 0;
        }
        return (int) Math.round(((double) getCompletedTasks() / tasks.size()) * 100.0);
    }

    // Habit progress: Completed Habits / Total Habits * 100
    public int getHabitProgressPercentage() {
        if (habits.isEmpty()) {
            return 0;
        }
        return (int) Math.round(((double) getCompletedHabits() / habits.size()) * 100.0);
    }

    // Goal progress: Average of all goals' percentages
    public int getGoalProgressPercentage() {
        if (goals.isEmpty()) {
            return 0;
        }
        int totalPercentage = 0;
        for (Goal goal : goals) {
            totalPercentage += goal.getPercentage();
        }
        return totalPercentage / goals.size();
    }

    // Overall progress: Simple average of task, goal, and habit progress
    public int getOverallProgressPercentage() {
        int categoriesWithData = 0;
        int sumOfPercentages = 0;

        if (!tasks.isEmpty()) {
            sumOfPercentages += getTaskProgressPercentage();
            categoriesWithData++;
        }
        if (!goals.isEmpty()) {
            sumOfPercentages += getGoalProgressPercentage();
            categoriesWithData++;
        }
        if (!habits.isEmpty()) {
            sumOfPercentages += getHabitProgressPercentage();
            categoriesWithData++;
        }

        if (categoriesWithData == 0) {
            return 0;
        }

        return sumOfPercentages / categoriesWithData;
    }
}
