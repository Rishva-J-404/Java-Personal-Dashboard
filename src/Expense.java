/**
 * Expense.java
 * Represents a single financial expense with description, category, and monetary amount.
 * Demonstrates: OOP Encapsulation and data modeling.
 */
public class Expense {
    // Private variables (Encapsulation)
    private String description;
    private String category;
    private double amount;

    // Constructor to initialize an expense
    public Expense(String description, String category, double amount) {
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Getter for category (Food, Transport, Education, Shopping, Other)
    public String getCategory() {
        return category;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // String representation of expense
    @Override
    public String toString() {
        return description + " (" + category + ") - \u20B9" + String.format("%.2f", amount);
    }
}
