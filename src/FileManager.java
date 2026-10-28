import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * FileManager.java
 * Handles persistent reading and writing of data to text files using pipe-delimited format.
 * Demonstrates: Java I/O (BufferedReader, BufferedWriter, FileReader, FileWriter)
 * and robust Exception Handling (try-catch-finally).
 */
public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String TASKS_FILE = "data/tasks.txt";
    private static final String GOALS_FILE = "data/goals.txt";
    private static final String HABITS_FILE = "data/habits.txt";
    private static final String EXPENSES_FILE = "data/expenses.txt";

    public FileManager() {
        // Ensure data directory exists
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // ==========================================
    // TASK FILE OPERATIONS
    // ==========================================

    public void saveTasks(ArrayList<Task> tasks) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(TASKS_FILE));
            for (Task task : tasks) {
                String status = task.isCompleted() ? "Completed" : "Pending";
                writer.write(task.getName() + "|" + task.getPriority() + "|" + status);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.err.println("Error closing tasks writer: " + e.getMessage());
            }
        }
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> list = new ArrayList<Task>();
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
            return list;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    String priority = parts[1].trim();
                    boolean completed = parts[2].trim().equalsIgnoreCase("Completed");
                    list.add(new Task(name, priority, completed));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.err.println("Error closing tasks reader: " + e.getMessage());
            }
        }
        return list;
    }

    // ==========================================
    // GOAL FILE OPERATIONS
    // ==========================================

    public void saveGoals(ArrayList<Goal> goals) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(GOALS_FILE));
            for (Goal goal : goals) {
                writer.write(goal.getName() + "|" + goal.getTarget() + "|" + goal.getProgress());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving goals: " + e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.err.println("Error closing goals writer: " + e.getMessage());
            }
        }
    }

    public ArrayList<Goal> loadGoals() {
        ArrayList<Goal> list = new ArrayList<Goal>();
        File file = new File(GOALS_FILE);
        if (!file.exists()) {
            return list;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    int target = Integer.parseInt(parts[1].trim());
                    int progress = Integer.parseInt(parts[2].trim());
                    list.add(new Goal(name, target, progress));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading goals: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.err.println("Error closing goals reader: " + e.getMessage());
            }
        }
        return list;
    }

    // ==========================================
    // HABIT FILE OPERATIONS
    // ==========================================

    public void saveHabits(ArrayList<Habit> habits) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(HABITS_FILE));
            for (Habit habit : habits) {
                String status = habit.isCompleted() ? "Completed" : "Pending";
                writer.write(habit.getName() + "|" + status);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving habits: " + e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.err.println("Error closing habits writer: " + e.getMessage());
            }
        }
    }

    public ArrayList<Habit> loadHabits() {
        ArrayList<Habit> list = new ArrayList<Habit>();
        File file = new File(HABITS_FILE);
        if (!file.exists()) {
            return list;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    boolean completed = parts[1].trim().equalsIgnoreCase("Completed");
                    list.add(new Habit(name, completed));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading habits: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.err.println("Error closing habits reader: " + e.getMessage());
            }
        }
        return list;
    }

    // ==========================================
    // EXPENSE FILE OPERATIONS
    // ==========================================

    public void saveExpenses(ArrayList<Expense> expenses) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(EXPENSES_FILE));
            for (Expense expense : expenses) {
                writer.write(expense.getDescription() + "|" + expense.getCategory() + "|" + expense.getAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.err.println("Error closing expenses writer: " + e.getMessage());
            }
        }
    }

    public ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> list = new ArrayList<Expense>();
        File file = new File(EXPENSES_FILE);
        if (!file.exists()) {
            return list;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String description = parts[0].trim();
                    String category = parts[1].trim();
                    double amount = Double.parseDouble(parts[2].trim());
                    list.add(new Expense(description, category, amount));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading expenses: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.err.println("Error closing expenses reader: " + e.getMessage());
            }
        }
        return list;
    }

    // ==========================================
    // CONVENIENCE METHODS FOR PRODUCTIVITY MANAGER
    // ==========================================

    public void loadAll(ProductivityManager manager) {
        ArrayList<Task> tasks = loadTasks();
        for (Task t : tasks) {
            manager.addTask(t);
        }

        ArrayList<Goal> goals = loadGoals();
        for (Goal g : goals) {
            manager.addGoal(g);
        }

        ArrayList<Habit> habits = loadHabits();
        for (Habit h : habits) {
            manager.addHabit(h);
        }

        ArrayList<Expense> expenses = loadExpenses();
        for (Expense e : expenses) {
            manager.addExpense(e);
        }
    }

    public void saveAll(ProductivityManager manager) {
        saveTasks(manager.getTasks());
        saveGoals(manager.getGoals());
        saveHabits(manager.getHabits());
        saveExpenses(manager.getExpenses());
    }
}
