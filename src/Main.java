import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main.java
 * The application entry point for the Personal Productivity Dashboard.
 * Demonstrates: Clean separation of concerns and launching a Swing GUI on the Event Dispatch Thread.
 */
public class Main {

    public static void main(String[] args) {
        // Set system look and feel for a modern, clean appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default Swing Look and Feel
            System.out.println("Using default Swing Look and Feel.");
        }

        // Launch GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Initialize the central productivity manager
                ProductivityManager manager = new ProductivityManager();

                // Create and display the main dashboard GUI
                new DashboardGUI(manager);
            }
        });
    }
}
