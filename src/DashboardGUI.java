import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * DashboardGUI.java
 * Main Swing User Interface for Personal Productivity Dashboard.
 * Demonstrates: Java Swing (JFrame, JPanel, CardLayout, JTable, JProgressBar,
 * JButton, JOptionPane) and event handling.
 */
public class DashboardGUI extends JFrame {

    // Reference to data manager and file manager
    private ProductivityManager manager;
    private FileManager fileManager;

    // Color Scheme (Section 10)
    public static final Color COLOR_BG = new Color(0xF5, 0xF7, 0xFA);
    public static final Color COLOR_SIDEBAR = new Color(0x17, 0x20, 0x33);
    public static final Color COLOR_SIDEBAR_HOVER = new Color(0x23, 0x2E, 0x4A);
    public static final Color COLOR_PRIMARY = new Color(0x4F, 0x46, 0xE5);
    public static final Color COLOR_PRIMARY_HOVER = new Color(0x43, 0x38, 0xCA);
    public static final Color COLOR_CARD = Color.WHITE;
    public static final Color COLOR_TEXT_MAIN = new Color(0x1F, 0x29, 0x37);
    public static final Color COLOR_TEXT_SECONDARY = new Color(0x6B, 0x72, 0x80);
    public static final Color COLOR_SUCCESS = new Color(0x22, 0xC5, 0x5E);
    public static final Color COLOR_WARNING = new Color(0xF5, 0x9E, 0x0B);
    public static final Color COLOR_DANGER = new Color(0xEF, 0x44, 0x44);
    public static final Color COLOR_BORDER = new Color(0xE5, 0xE7, 0xEB);

    // Main CardLayout for screen switching
    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    // Table Models for reactive updates
    private DefaultTableModel taskTableModel;
    private DefaultTableModel goalTableModel;
    private DefaultTableModel habitTableModel;
    private DefaultTableModel expenseTableModel;

    // Dashboard dynamic labels
    private JLabel dashTasksCountLabel;
    private JLabel dashGoalsPercentLabel;
    private JLabel dashHabitsCountLabel;
    private JLabel dashExpenseTotalLabel;
    private JLabel dashOverallPercentLabel;
    private JProgressBar dashOverallProgressBar;
    private JPanel dashRecentActivityPanel;

    // Progress Screen dynamic components
    private JLabel progOverallLabel;
    private JProgressBar progOverallBar;
    private JLabel progTasksDetailLabel;
    private JProgressBar progTasksBar;
    private JLabel progGoalsDetailLabel;
    private JProgressBar progGoalsBar;
    private JLabel progHabitsDetailLabel;
    private JProgressBar progHabitsBar;
    private JLabel progExpenseDetailLabel;

    // Expense Screen dynamic label
    private JLabel expenseTotalCardLabel;

    // Number format for currency
    private DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");

    public DashboardGUI(ProductivityManager manager) {
        this.manager = manager;
        this.fileManager = new FileManager();

        // 1. Load initial data from text files
        fileManager.loadAll(manager);

        // 2. Setup JFrame properties
        setTitle("Personal Productivity Dashboard");
        setSize(1020, 700);
        setMinimumSize(new Dimension(880, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // 3. Setup Layout
        setLayout(new BorderLayout());

        // Left Sidebar
        JPanel sidebarPanel = createSidebar();
        add(sidebarPanel, BorderLayout.WEST);

        // Right Content Area with CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(COLOR_BG);

        // Add Screens to CardLayout
        mainContentPanel.add(createDashboardScreen(), "DASHBOARD");
        mainContentPanel.add(createTasksScreen(), "TASKS");
        mainContentPanel.add(createGoalsScreen(), "GOALS");
        mainContentPanel.add(createHabitsScreen(), "HABITS");
        mainContentPanel.add(createExpensesScreen(), "EXPENSES");
        mainContentPanel.add(createProgressScreen(), "PROGRESS");
        mainContentPanel.add(createHankScreen(), "HANK");
        mainContentPanel.add(createSettingsScreen(), "SETTINGS");

        add(mainContentPanel, BorderLayout.CENTER);

        // Initial refresh
        refreshAllScreens();

        // Display the window
        setVisible(true);
    }

    // =========================================================================
    // SIDEBAR NAVIGATION (Section 11)
    // =========================================================================

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(230, 700));
        sidebar.setBackground(COLOR_SIDEBAR);

        // Header / Logo Area
        JPanel brandPanel = new JPanel();
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setBackground(COLOR_SIDEBAR);
        brandPanel.setBorder(new EmptyBorder(25, 20, 20, 20));

        JLabel appTitle1 = new JLabel("PERSONAL");
        appTitle1.setFont(new Font("Segoe UI", Font.BOLD, 17));
        appTitle1.setForeground(Color.WHITE);

        JLabel appTitle2 = new JLabel("PRODUCTIVITY");
        appTitle2.setFont(new Font("Segoe UI", Font.BOLD, 17));
        appTitle2.setForeground(new Color(0x81, 0x8C, 0xF8)); // Light indigo

        JLabel appSubtitle = new JLabel("Dashboard v1.0");
        appSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        appSubtitle.setForeground(COLOR_TEXT_SECONDARY);

        brandPanel.add(appTitle1);
        brandPanel.add(appTitle2);
        brandPanel.add(Box.createVerticalStrut(4));
        brandPanel.add(appSubtitle);

        sidebar.add(brandPanel, BorderLayout.NORTH);

        // Navigation Buttons Container
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(COLOR_SIDEBAR);
        navPanel.setBorder(new EmptyBorder(10, 12, 10, 12));

        // Navigation Items
        JButton btnDash = createNavButton("Dashboard", "DASHBOARD", "DASHBOARD");
        JButton btnTasks = createNavButton("Tasks", "TASKS", "TASKS");
        JButton btnGoals = createNavButton("Goals", "GOALS", "GOALS");
        JButton btnHabits = createNavButton("Habits", "HABITS", "HABITS");
        JButton btnExpenses = createNavButton("Expenses", "EXPENSES", "EXPENSES");
        JButton btnProgress = createNavButton("Progress", "PROGRESS", "PROGRESS");

        navPanel.add(btnDash);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnTasks);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnGoals);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnHabits);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnExpenses);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnProgress);

        // Divider
        navPanel.add(Box.createVerticalStrut(16));
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(200, 1));
        sep.setForeground(new Color(0x33, 0x41, 0x55));
        navPanel.add(sep);
        navPanel.add(Box.createVerticalStrut(16));

        // Future Hank and Settings
        JButton btnHank = createNavButton("Hank (AI)", "HANK", "HANK");
        JButton btnSettings = createNavButton("Settings", "SETTINGS", "SETTINGS");

        navPanel.add(btnHank);
        navPanel.add(Box.createVerticalStrut(6));
        navPanel.add(btnSettings);

        sidebar.add(navPanel, BorderLayout.CENTER);

        // Footer in Sidebar
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        footerPanel.setBackground(COLOR_SIDEBAR);
        JLabel footerLabel = new JLabel("College Java Project");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        footerLabel.setForeground(COLOR_TEXT_SECONDARY);
        footerPanel.add(footerLabel);
        sidebar.add(footerPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private Icon createNavIcon(final String type) {
        return new Icon() {
            @Override
            public int getIconWidth() { return 18; }
            @Override
            public int getIconHeight() { return 18; }
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0x81, 0x8C, 0xF8)); // Light indigo accent
                g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                int ox = x + 1;
                int oy = y + 1;

                if ("DASHBOARD".equals(type)) {
                    g2.drawRoundRect(ox, oy, 6, 6, 2, 2);
                    g2.drawRoundRect(ox + 8, oy, 6, 6, 2, 2);
                    g2.drawRoundRect(ox, oy + 8, 6, 6, 2, 2);
                    g2.drawRoundRect(ox + 8, oy + 8, 6, 6, 2, 2);
                } else if ("TASKS".equals(type)) {
                    g2.drawRoundRect(ox, oy, 14, 14, 3, 3);
                    g2.drawLine(ox + 3, oy + 7, ox + 6, oy + 10);
                    g2.drawLine(ox + 6, oy + 10, ox + 11, oy + 4);
                } else if ("GOALS".equals(type)) {
                    g2.drawOval(ox, oy, 14, 14);
                    g2.drawOval(ox + 4, oy + 4, 6, 6);
                } else if ("HABITS".equals(type)) {
                    g2.drawArc(ox, oy, 14, 14, 45, 270);
                    g2.drawLine(ox + 10, oy + 1, ox + 13, oy + 4);
                    g2.drawLine(ox + 13, oy + 4, ox + 10, oy + 7);
                } else if ("EXPENSES".equals(type)) {
                    g2.drawRoundRect(ox, oy + 2, 14, 10, 2, 2);
                    g2.drawLine(ox, oy + 5, ox + 14, oy + 5);
                } else if ("PROGRESS".equals(type)) {
                    g2.drawLine(ox, oy + 14, ox + 14, oy + 14);
                    g2.drawLine(ox + 2, oy + 14, ox + 2, oy + 9);
                    g2.drawLine(ox + 6, oy + 14, ox + 6, oy + 5);
                    g2.drawLine(ox + 10, oy + 14, ox + 10, oy + 2);
                } else if ("HANK".equals(type)) {
                    g2.drawRoundRect(ox + 1, oy + 3, 12, 10, 3, 3);
                    g2.drawLine(ox + 7, oy, ox + 7, oy + 3);
                    g2.fillOval(ox + 4, oy + 6, 2, 2);
                    g2.fillOval(ox + 9, oy + 6, 2, 2);
                    g2.drawLine(ox + 4, oy + 10, ox + 10, oy + 10);
                } else if ("SETTINGS".equals(type)) {
                    g2.drawOval(ox + 3, oy + 3, 8, 8);
                    g2.drawLine(ox + 7, oy, ox + 7, oy + 3);
                    g2.drawLine(ox + 7, oy + 11, ox + 7, oy + 14);
                    g2.drawLine(ox, oy + 7, ox + 3, oy + 7);
                    g2.drawLine(ox + 11, oy + 7, ox + 14, oy + 7);
                }
                g2.dispose();
            }
        };
    }

    private Icon createHankBigIcon() {
        return new Icon() {
            @Override
            public int getIconWidth() { return 64; }
            @Override
            public int getIconHeight() { return 64; }
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_PRIMARY);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Antenna
                g2.drawLine(x + 32, y + 8, x + 32, y + 16);
                g2.fillOval(x + 29, y + 3, 6, 6);

                // Head
                g2.drawRoundRect(x + 10, y + 16, 44, 38, 10, 10);

                // Eyes
                g2.fillOval(x + 20, y + 28, 8, 8);
                g2.fillOval(x + 36, y + 28, 8, 8);

                // Mouth
                g2.drawLine(x + 22, y + 44, x + 42, y + 44);

                // Ears
                g2.fillRect(x + 5, y + 28, 5, 12);
                g2.fillRect(x + 54, y + 28, 5, 12);

                g2.dispose();
            }
        };
    }

    private JButton createNavButton(String text, final String cardName, String iconType) {
        final JButton button = new JButton(text);
        button.setIcon(createNavIcon(iconType));
        button.setIconTextGap(12);
        button.setMaximumSize(new Dimension(206, 40));
        button.setPreferredSize(new Dimension(206, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(new Color(0xE2, 0xE8, 0xF0));
        button.setBackground(COLOR_SIDEBAR);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8, 14, 8, 14));

        // Hover effect using mouse listener
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_SIDEBAR_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COLOR_SIDEBAR);
            }
        });

        // Click action: switch card and refresh
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContentPanel, cardName);
                refreshAllScreens();
            }
        });

        return button;
    }

    // =========================================================================
    // 1. DASHBOARD SCREEN (Section 9)
    // =========================================================================

    private JPanel createDashboardScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setBackground(COLOR_BG);
        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Here's your productivity overview for today.");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        headerPanel.add(welcomeLabel);
        headerPanel.add(subLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content Area (Cards & Progress)
        JPanel contentArea = new JPanel();
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBackground(COLOR_BG);
        contentArea.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Metric Cards Grid (Tasks, Goals, Habits, Expenses)
        JPanel cardsGrid = new JPanel(new GridLayout(1, 4, 15, 0));
        cardsGrid.setBackground(COLOR_BG);
        cardsGrid.setMaximumSize(new Dimension(1000, 100));

        dashTasksCountLabel = new JLabel("0 / 0", SwingConstants.CENTER);
        dashGoalsPercentLabel = new JLabel("0%", SwingConstants.CENTER);
        dashHabitsCountLabel = new JLabel("0 / 0", SwingConstants.CENTER);
        dashExpenseTotalLabel = new JLabel("\u20B90.00", SwingConstants.CENTER);

        cardsGrid.add(createMetricCard("TASKS", dashTasksCountLabel, COLOR_PRIMARY));
        cardsGrid.add(createMetricCard("GOALS", dashGoalsPercentLabel, COLOR_SUCCESS));
        cardsGrid.add(createMetricCard("HABITS", dashHabitsCountLabel, COLOR_WARNING));
        cardsGrid.add(createMetricCard("EXPENSES", dashExpenseTotalLabel, COLOR_DANGER));

        contentArea.add(cardsGrid);
        contentArea.add(Box.createVerticalStrut(20));

        // Overall Progress Big Card
        JPanel overallCard = createCardPanel();
        overallCard.setLayout(new BorderLayout(15, 15));
        overallCard.setMaximumSize(new Dimension(1000, 130));

        JPanel progHeader = new JPanel(new BorderLayout());
        progHeader.setBackground(COLOR_CARD);
        JLabel overallTitle = new JLabel("Overall Progress");
        overallTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        overallTitle.setForeground(COLOR_TEXT_MAIN);

        dashOverallPercentLabel = new JLabel("0%", SwingConstants.RIGHT);
        dashOverallPercentLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        dashOverallPercentLabel.setForeground(COLOR_PRIMARY);

        progHeader.add(overallTitle, BorderLayout.WEST);
        progHeader.add(dashOverallPercentLabel, BorderLayout.EAST);

        dashOverallProgressBar = new JProgressBar(0, 100);
        dashOverallProgressBar.setValue(0);
        dashOverallProgressBar.setPreferredSize(new Dimension(100, 24));
        dashOverallProgressBar.setForeground(COLOR_PRIMARY);
        dashOverallProgressBar.setBackground(new Color(0xE0, 0xE7, 0xFF));
        dashOverallProgressBar.setStringPainted(true);
        dashOverallProgressBar.setFont(new Font("Segoe UI", Font.BOLD, 12));

        overallCard.add(progHeader, BorderLayout.NORTH);
        overallCard.add(dashOverallProgressBar, BorderLayout.CENTER);

        contentArea.add(overallCard);
        contentArea.add(Box.createVerticalStrut(20));

        // Recent Activity / Quick Status Card
        JPanel activityCard = createCardPanel();
        activityCard.setLayout(new BorderLayout());

        JLabel activityTitle = new JLabel("Productivity Summary & Activity");
        activityTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        activityTitle.setForeground(COLOR_TEXT_MAIN);
        activityTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        dashRecentActivityPanel = new JPanel();
        dashRecentActivityPanel.setLayout(new BoxLayout(dashRecentActivityPanel, BoxLayout.Y_AXIS));
        dashRecentActivityPanel.setBackground(COLOR_CARD);

        activityCard.add(activityTitle, BorderLayout.NORTH);
        activityCard.add(new JScrollPane(dashRecentActivityPanel), BorderLayout.CENTER);

        contentArea.add(activityCard);

        panel.add(contentArea, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMetricCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = createCardPanel();
        card.setLayout(new BorderLayout(0, 8));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(COLOR_TEXT_SECONDARY);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // =========================================================================
    // 2. TASK MANAGEMENT SCREEN (Section 12)
    // =========================================================================

    private JPanel createTasksScreen() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header & Actions Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(COLOR_BG);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 4));
        titlePanel.setBackground(COLOR_BG);
        JLabel titleLabel = new JLabel("Tasks");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Manage and organize your daily tasks");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        titlePanel.add(titleLabel);
        titlePanel.add(subLabel);
        topBar.add(titlePanel, BorderLayout.WEST);

        // Action Buttons: + Add Task, ✓ Complete, Delete
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonGroup.setBackground(COLOR_BG);

        JButton btnAdd = createStyledButton("+ Add Task", COLOR_PRIMARY);
        JButton btnComplete = createStyledButton("Mark Completed", COLOR_SUCCESS);
        JButton btnDelete = createStyledButton("Delete Task", COLOR_DANGER);

        buttonGroup.add(btnAdd);
        buttonGroup.add(btnComplete);
        buttonGroup.add(btnDelete);
        topBar.add(buttonGroup, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Tasks JTable
        String[] columns = {"Task", "Priority", "Status"};
        taskTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Non-editable table cells
            }
        };

        final JTable taskTable = new JTable(taskTableModel);
        styleTable(taskTable);

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Button Actions
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddTaskDialog();
            }
        });

        btnComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select a task from the table to complete.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Task task = manager.getTasks().get(selectedRow);
                task.markCompleted();
                fileManager.saveTasks(manager.getTasks());
                refreshAllScreens();
                JOptionPane.showMessageDialog(DashboardGUI.this,
                        "Task marked as Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select a task from the table to delete.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(DashboardGUI.this,
                        "Are you sure you want to delete this task?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeTask(selectedRow);
                    fileManager.saveTasks(manager.getTasks());
                    refreshAllScreens();
                }
            }
        });

        return panel;
    }

    private void showAddTaskDialog() {
        JTextField nameField = new JTextField(20);
        String[] priorities = {"High", "Medium", "Low"};
        JComboBox<String> priorityCombo = new JComboBox<String>(priorities);

        JPanel dialogPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dialogPanel.add(new JLabel("Task Name:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Priority:"));
        dialogPanel.add(priorityCombo);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "+ Add New Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            // Input Validation (Section 12 & 18)
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Task name cannot be empty. Please enter a valid name.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String priority = (String) priorityCombo.getSelectedItem();
            Task newTask = new Task(name, priority);
            manager.addTask(newTask);

            // Save to file immediately (Section 7)
            fileManager.saveTasks(manager.getTasks());
            refreshAllScreens();
        }
    }

    // =========================================================================
    // 3. GOAL TRACKING SCREEN (Section 13)
    // =========================================================================

    private JPanel createGoalsScreen() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(COLOR_BG);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 4));
        titlePanel.setBackground(COLOR_BG);
        JLabel titleLabel = new JLabel("Goals");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Track your long-term milestones and progress");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        titlePanel.add(titleLabel);
        titlePanel.add(subLabel);
        topBar.add(titlePanel, BorderLayout.WEST);

        // Action Buttons: + Add Goal, Update Progress, Delete
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonGroup.setBackground(COLOR_BG);

        JButton btnAdd = createStyledButton("+ Add Goal", COLOR_PRIMARY);
        JButton btnUpdate = createStyledButton("Update Progress", COLOR_WARNING);
        JButton btnDelete = createStyledButton("Delete", COLOR_DANGER);

        buttonGroup.add(btnAdd);
        buttonGroup.add(btnUpdate);
        buttonGroup.add(btnDelete);
        topBar.add(buttonGroup, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Goals Table
        String[] columns = {"Goal Name", "Target", "Current Progress", "Completion %"};
        goalTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final JTable goalTable = new JTable(goalTableModel);
        styleTable(goalTable);

        JScrollPane scrollPane = new JScrollPane(goalTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddGoalDialog();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = goalTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select a goal to update its progress.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Goal goal = manager.getGoals().get(selectedRow);
                showUpdateGoalDialog(goal);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = goalTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select a goal to delete.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(DashboardGUI.this,
                        "Are you sure you want to delete this goal?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeGoal(selectedRow);
                    fileManager.saveGoals(manager.getGoals());
                    refreshAllScreens();
                }
            }
        });

        return panel;
    }

    private void showAddGoalDialog() {
        JTextField nameField = new JTextField(20);
        JTextField targetField = new JTextField(10);

        JPanel dialogPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        dialogPanel.add(new JLabel("Goal Name:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Target (Numeric e.g. 100):"));
        dialogPanel.add(targetField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "+ Add New Goal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Goal name cannot be empty.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Exception Handling (Section 18)
            try {
                int target = Integer.parseInt(targetField.getText().trim());
                if (target <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Target must be a positive integer greater than zero.",
                            "Invalid Target", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Goal newGoal = new Goal(name, target);
                manager.addGoal(newGoal);
                fileManager.saveGoals(manager.getGoals());
                refreshAllScreens();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid whole number for the target.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUpdateGoalDialog(Goal goal) {
        String input = JOptionPane.showInputDialog(this,
                "Enter new progress for \"" + goal.getName() + "\"\n(Current: " +
                        goal.getProgress() + " / Target: " + goal.getTarget() + "):",
                "Update Goal Progress", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            // Exception Handling (Section 18)
            try {
                int newProgress = Integer.parseInt(input.trim());
                if (newProgress < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Progress cannot be negative.",
                            "Invalid Value", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                goal.updateProgress(newProgress);
                fileManager.saveGoals(manager.getGoals());
                refreshAllScreens();

                JOptionPane.showMessageDialog(this,
                        "Progress updated to " + goal.getProgress() + "/" + goal.getTarget() +
                                " (" + goal.getPercentage() + "%)",
                        "Updated", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric value for progress.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =========================================================================
    // 4. HABIT TRACKING SCREEN (Section 14)
    // =========================================================================

    private JPanel createHabitsScreen() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(COLOR_BG);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 4));
        titlePanel.setBackground(COLOR_BG);
        JLabel titleLabel = new JLabel("Habits");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Build better daily routines and stay consistent");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        titlePanel.add(titleLabel);
        titlePanel.add(subLabel);
        topBar.add(titlePanel, BorderLayout.WEST);

        // Buttons: + Add Habit, Toggle Complete, Delete
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonGroup.setBackground(COLOR_BG);

        JButton btnAdd = createStyledButton("+ Add Habit", COLOR_PRIMARY);
        JButton btnToggle = createStyledButton("Toggle / Complete", COLOR_SUCCESS);
        JButton btnDelete = createStyledButton("Delete", COLOR_DANGER);

        buttonGroup.add(btnAdd);
        buttonGroup.add(btnToggle);
        buttonGroup.add(btnDelete);
        topBar.add(buttonGroup, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Habits Table
        String[] columns = {"Habit Name", "Daily Status"};
        habitTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final JTable habitTable = new JTable(habitTableModel);
        styleTable(habitTable);

        JScrollPane scrollPane = new JScrollPane(habitTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddHabitDialog();
            }
        });

        btnToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = habitTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select a habit to toggle status.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Habit habit = manager.getHabits().get(selectedRow);
                // Toggle status
                habit.setCompleted(!habit.isCompleted());
                fileManager.saveHabits(manager.getHabits());
                refreshAllScreens();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = habitTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select a habit to delete.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(DashboardGUI.this,
                        "Are you sure you want to delete this habit?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeHabit(selectedRow);
                    fileManager.saveHabits(manager.getHabits());
                    refreshAllScreens();
                }
            }
        });

        return panel;
    }

    private void showAddHabitDialog() {
        JTextField nameField = new JTextField(20);

        JPanel dialogPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        dialogPanel.add(new JLabel("Habit Name:"));
        dialogPanel.add(nameField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "+ Add New Habit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Habit name cannot be empty.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Habit newHabit = new Habit(name);
            manager.addHabit(newHabit);
            fileManager.saveHabits(manager.getHabits());
            refreshAllScreens();
        }
    }

    // =========================================================================
    // 5. EXPENSE TRACKING SCREEN (Section 15)
    // =========================================================================

    private JPanel createExpensesScreen() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Top Header
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(COLOR_BG);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 4));
        titlePanel.setBackground(COLOR_BG);
        JLabel titleLabel = new JLabel("Expenses");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Track your spending by category");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        titlePanel.add(titleLabel);
        titlePanel.add(subLabel);
        topBar.add(titlePanel, BorderLayout.WEST);

        // Buttons
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonGroup.setBackground(COLOR_BG);

        JButton btnAdd = createStyledButton("+ Add Expense", COLOR_PRIMARY);
        JButton btnDelete = createStyledButton("Delete Expense", COLOR_DANGER);

        buttonGroup.add(btnAdd);
        buttonGroup.add(btnDelete);
        topBar.add(buttonGroup, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Center Area (Total Banner + Table)
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(COLOR_BG);

        // Total Spending Banner
        JPanel totalBanner = createCardPanel();
        totalBanner.setLayout(new BorderLayout());
        totalBanner.setPreferredSize(new Dimension(800, 60));

        JLabel totalTitle = new JLabel("Total Expenses:");
        totalTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        totalTitle.setForeground(COLOR_TEXT_SECONDARY);

        expenseTotalCardLabel = new JLabel("\u20B90.00", SwingConstants.RIGHT);
        expenseTotalCardLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        expenseTotalCardLabel.setForeground(COLOR_DANGER);

        totalBanner.add(totalTitle, BorderLayout.WEST);
        totalBanner.add(expenseTotalCardLabel, BorderLayout.EAST);
        centerPanel.add(totalBanner, BorderLayout.NORTH);

        // Expenses Table
        String[] columns = {"Description", "Category", "Amount (\u20B9)"};
        expenseTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final JTable expenseTable = new JTable(expenseTableModel);
        styleTable(expenseTable);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddExpenseDialog();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = expenseTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DashboardGUI.this,
                            "Please select an expense to delete.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(DashboardGUI.this,
                        "Are you sure you want to delete this expense?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.removeExpense(selectedRow);
                    fileManager.saveExpenses(manager.getExpenses());
                    refreshAllScreens();
                }
            }
        });

        return panel;
    }

    private void showAddExpenseDialog() {
        JTextField descField = new JTextField(20);
        String[] categories = {"Food", "Transport", "Education", "Shopping", "Other"};
        JComboBox<String> categoryCombo = new JComboBox<String>(categories);
        JTextField amountField = new JTextField(10);

        JPanel dialogPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        dialogPanel.add(new JLabel("Description:"));
        dialogPanel.add(descField);
        dialogPanel.add(new JLabel("Category:"));
        dialogPanel.add(categoryCombo);
        dialogPanel.add(new JLabel("Amount (\u20B9):"));
        dialogPanel.add(amountField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel,
                "+ Add New Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String desc = descField.getText().trim();
            if (desc.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Expense description cannot be empty.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Exception Handling (Section 18)
            try {
                double amount = Double.parseDouble(amountField.getText().trim());
                if (amount < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Amount cannot be negative.",
                            "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String category = (String) categoryCombo.getSelectedItem();
                Expense expense = new Expense(desc, category, amount);
                manager.addExpense(expense);
                fileManager.saveExpenses(manager.getExpenses());
                refreshAllScreens();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric amount (e.g. 150 or 49.50).",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =========================================================================
    // 6. PROGRESS SCREEN (Section 16 & 17)
    // =========================================================================

    private JPanel createProgressScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setBackground(COLOR_BG);
        JLabel titleLabel = new JLabel("Productivity Progress");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Comprehensive calculation of all active trackers");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main Progress Container
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // 1. Overall Progress Big Card
        JPanel overallCard = createCardPanel();
        overallCard.setLayout(new BorderLayout(15, 12));
        overallCard.setMaximumSize(new Dimension(1000, 140));

        JPanel overallHead = new JPanel(new BorderLayout());
        overallHead.setBackground(COLOR_CARD);

        JLabel overallTitle = new JLabel("Overall Progress");
        overallTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        overallTitle.setForeground(COLOR_TEXT_MAIN);

        progOverallLabel = new JLabel("0%", SwingConstants.RIGHT);
        progOverallLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        progOverallLabel.setForeground(COLOR_PRIMARY);

        overallHead.add(overallTitle, BorderLayout.WEST);
        overallHead.add(progOverallLabel, BorderLayout.EAST);

        progOverallBar = new JProgressBar(0, 100);
        progOverallBar.setValue(0);
        progOverallBar.setPreferredSize(new Dimension(100, 28));
        progOverallBar.setForeground(COLOR_PRIMARY);
        progOverallBar.setBackground(new Color(0xE0, 0xE7, 0xFF));
        progOverallBar.setStringPainted(true);
        progOverallBar.setFont(new Font("Segoe UI", Font.BOLD, 13));

        overallCard.add(overallHead, BorderLayout.NORTH);
        overallCard.add(progOverallBar, BorderLayout.CENTER);

        contentPanel.add(overallCard);
        contentPanel.add(Box.createVerticalStrut(20));

        // 2. Breakdown Cards Grid (Tasks, Goals, Habits, Expenses)
        JPanel detailsGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        detailsGrid.setBackground(COLOR_BG);

        // Tasks Detail Card
        JPanel tasksCard = createCardPanel();
        tasksCard.setLayout(new BorderLayout(0, 10));
        JLabel tasksCardTitle = new JLabel("Tasks Completion");
        tasksCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        progTasksDetailLabel = new JLabel("0 / 0 completed (0%)");
        progTasksDetailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        progTasksBar = new JProgressBar(0, 100);
        progTasksBar.setForeground(COLOR_PRIMARY);
        progTasksBar.setStringPainted(true);
        tasksCard.add(tasksCardTitle, BorderLayout.NORTH);
        tasksCard.add(progTasksDetailLabel, BorderLayout.CENTER);
        tasksCard.add(progTasksBar, BorderLayout.SOUTH);

        // Goals Detail Card
        JPanel goalsCard = createCardPanel();
        goalsCard.setLayout(new BorderLayout(0, 10));
        JLabel goalsCardTitle = new JLabel("Goals Milestone Progress");
        goalsCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        progGoalsDetailLabel = new JLabel("Average Completion: 0%");
        progGoalsDetailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        progGoalsBar = new JProgressBar(0, 100);
        progGoalsBar.setForeground(COLOR_SUCCESS);
        progGoalsBar.setStringPainted(true);
        goalsCard.add(goalsCardTitle, BorderLayout.NORTH);
        goalsCard.add(progGoalsDetailLabel, BorderLayout.CENTER);
        goalsCard.add(progGoalsBar, BorderLayout.SOUTH);

        // Habits Detail Card
        JPanel habitsCard = createCardPanel();
        habitsCard.setLayout(new BorderLayout(0, 10));
        JLabel habitsCardTitle = new JLabel("Daily Habits Consistency");
        habitsCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        progHabitsDetailLabel = new JLabel("0 / 0 completed (0%)");
        progHabitsDetailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        progHabitsBar = new JProgressBar(0, 100);
        progHabitsBar.setForeground(COLOR_WARNING);
        progHabitsBar.setStringPainted(true);
        habitsCard.add(habitsCardTitle, BorderLayout.NORTH);
        habitsCard.add(progHabitsDetailLabel, BorderLayout.CENTER);
        habitsCard.add(progHabitsBar, BorderLayout.SOUTH);

        // Expenses Summary Card
        JPanel expCard = createCardPanel();
        expCard.setLayout(new BorderLayout(0, 10));
        JLabel expCardTitle = new JLabel("Financial Expenses Total");
        expCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        progExpenseDetailLabel = new JLabel("Total Spending: \u20B90.00");
        progExpenseDetailLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        progExpenseDetailLabel.setForeground(COLOR_DANGER);
        JLabel expSubNote = new JLabel("Track spending to maintain budgeting balance");
        expSubNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        expSubNote.setForeground(COLOR_TEXT_SECONDARY);
        expCard.add(expCardTitle, BorderLayout.NORTH);
        expCard.add(progExpenseDetailLabel, BorderLayout.CENTER);
        expCard.add(expSubNote, BorderLayout.SOUTH);

        detailsGrid.add(tasksCard);
        detailsGrid.add(goalsCard);
        detailsGrid.add(habitsCard);
        detailsGrid.add(expCard);

        contentPanel.add(detailsGrid);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================================
    // 7. HANK AI PLACEHOLDER SCREEN (Section 22)
    // =========================================================================

    private JPanel createHankScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_BG);

        // Centered Card
        JPanel card = createCardPanel();
        card.setPreferredSize(new Dimension(500, 420));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Robot Icon
        JLabel robotLabel = new JLabel(createHankBigIcon(), SwingConstants.CENTER);
        robotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("HANK", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(COLOR_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Your AI Productivity Assistant", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subtitleLabel.setForeground(COLOR_TEXT_MAIN);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Future message
        JLabel noteLabel = new JLabel("AI features will be added in a future version.", SwingConstants.CENTER);
        noteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noteLabel.setForeground(COLOR_TEXT_SECONDARY);
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel detailLabel = new JLabel("Planned: RAG + LLM for smart personal insights & schedule optimization.", SwingConstants.CENTER);
        detailLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        detailLabel.setForeground(COLOR_TEXT_SECONDARY);
        detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button [ Ask Hank ]
        JButton btnAskHank = createStyledButton("Ask Hank", COLOR_PRIMARY);
        btnAskHank.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAskHank.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAskHank.setPreferredSize(new Dimension(160, 42));
        btnAskHank.setMaximumSize(new Dimension(160, 42));

        btnAskHank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Section 22 requirement
                JOptionPane.showMessageDialog(DashboardGUI.this,
                        "Hank AI Assistant will be available\nin the future version.",
                        "Hank AI Assistant",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        card.add(Box.createVerticalStrut(30));
        card.add(robotLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(noteLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(detailLabel);
        card.add(Box.createVerticalStrut(30));
        card.add(btnAskHank);
        card.add(Box.createVerticalStrut(20));

        panel.add(card);
        return panel;
    }

    // =========================================================================
    // 8. SETTINGS SCREEN
    // =========================================================================

    private JPanel createSettingsScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        headerPanel.setBackground(COLOR_BG);
        JLabel titleLabel = new JLabel("Settings & Data Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        JLabel subLabel = new JLabel("Application configuration and file persistence");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(COLOR_TEXT_SECONDARY);

        headerPanel.add(titleLabel);
        headerPanel.add(subLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Storage Info Card
        JPanel infoCard = createCardPanel();
        infoCard.setLayout(new GridLayout(5, 1, 8, 8));
        infoCard.setMaximumSize(new Dimension(1000, 160));

        JLabel infoHeader = new JLabel("Flat-File Persistence (Plain Text Files):");
        infoHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        infoHeader.setForeground(COLOR_TEXT_MAIN);

        JLabel path1 = new JLabel("\u2022 Tasks File: data/tasks.txt (Format: name|priority|status)");
        JLabel path2 = new JLabel("\u2022 Goals File: data/goals.txt (Format: name|target|progress)");
        JLabel path3 = new JLabel("\u2022 Habits File: data/habits.txt (Format: name|status)");
        JLabel path4 = new JLabel("\u2022 Expenses File: data/expenses.txt (Format: description|category|amount)");

        infoCard.add(infoHeader);
        infoCard.add(path1);
        infoCard.add(path2);
        infoCard.add(path3);
        infoCard.add(path4);

        contentPanel.add(infoCard);
        contentPanel.add(Box.createVerticalStrut(20));

        // Actions Card (Manual Save / Reload)
        JPanel actionsCard = createCardPanel();
        actionsCard.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        actionsCard.setMaximumSize(new Dimension(1000, 70));

        JButton btnSaveAll = createStyledButton("\uD83D\uDCBE Save All Data Now", COLOR_PRIMARY);
        JButton btnReloadAll = createStyledButton("\u21BB Reload From Files", COLOR_SIDEBAR);

        btnSaveAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileManager.saveAll(manager);
                JOptionPane.showMessageDialog(DashboardGUI.this,
                        "All data successfully saved to the 'data/' folder!",
                        "Saved", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnReloadAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear and reload
                manager.getTasks().clear();
                manager.getGoals().clear();
                manager.getHabits().clear();
                manager.getExpenses().clear();
                fileManager.loadAll(manager);
                refreshAllScreens();
                JOptionPane.showMessageDialog(DashboardGUI.this,
                        "All data reloaded from disk files!",
                        "Reloaded", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        actionsCard.add(btnSaveAll);
        actionsCard.add(btnReloadAll);

        contentPanel.add(actionsCard);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================================
    // DYNAMIC DATA REFRESH LOGIC
    // =========================================================================

    public void refreshAllScreens() {
        // 1. Refresh Tasks Table
        if (taskTableModel != null) {
            taskTableModel.setRowCount(0);
            for (Task t : manager.getTasks()) {
                String status = t.isCompleted() ? "Completed" : "Pending";
                taskTableModel.addRow(new Object[]{t.getName(), t.getPriority(), status});
            }
        }

        // 2. Refresh Goals Table
        if (goalTableModel != null) {
            goalTableModel.setRowCount(0);
            for (Goal g : manager.getGoals()) {
                goalTableModel.addRow(new Object[]{
                        g.getName(),
                        g.getTarget(),
                        g.getProgress(),
                        g.getPercentage() + "%"
                });
            }
        }

        // 3. Refresh Habits Table
        if (habitTableModel != null) {
            habitTableModel.setRowCount(0);
            for (Habit h : manager.getHabits()) {
                String status = h.isCompleted() ? "Completed" : "Pending";
                habitTableModel.addRow(new Object[]{h.getName(), status});
            }
        }

        // 4. Refresh Expenses Table
        if (expenseTableModel != null) {
            expenseTableModel.setRowCount(0);
            for (Expense exp : manager.getExpenses()) {
                expenseTableModel.addRow(new Object[]{
                        exp.getDescription(),
                        exp.getCategory(),
                        "\u20B9" + currencyFormat.format(exp.getAmount())
                });
            }
        }

        // 5. Update Metrics & Calculations (Section 17)
        int totalTasks = manager.getTasks().size();
        int completedTasks = manager.getCompletedTasks();
        int taskPercent = manager.getTaskProgressPercentage();

        int totalHabits = manager.getHabits().size();
        int completedHabits = manager.getCompletedHabits();
        int habitPercent = manager.getHabitProgressPercentage();

        int goalPercent = manager.getGoalProgressPercentage();
        double totalExpense = manager.getTotalExpenses();
        int overallScore = manager.getOverallProgressPercentage();

        // 6. Update Dashboard Widgets
        if (dashTasksCountLabel != null) {
            dashTasksCountLabel.setText(completedTasks + " / " + totalTasks);
            dashGoalsPercentLabel.setText(goalPercent + "%");
            dashHabitsCountLabel.setText(completedHabits + " / " + totalHabits);
            dashExpenseTotalLabel.setText("\u20B9" + currencyFormat.format(totalExpense));
            dashOverallPercentLabel.setText(overallScore + "%");
            dashOverallProgressBar.setValue(overallScore);

            // Update Activity Panel
            dashRecentActivityPanel.removeAll();
            dashRecentActivityPanel.add(createActivityItem("Tasks Status: " + completedTasks + " of " + totalTasks + " completed (" + taskPercent + "%)"));
            dashRecentActivityPanel.add(createActivityItem("Goals Milestone: Average completion at " + goalPercent + "% across " + manager.getGoals().size() + " active goals"));
            dashRecentActivityPanel.add(createActivityItem("Daily Habits: " + completedHabits + " of " + totalHabits + " habits finished today (" + habitPercent + "%)"));
            dashRecentActivityPanel.add(createActivityItem("Total Recorded Expenses: \u20B9" + currencyFormat.format(totalExpense) + " spent"));
            dashRecentActivityPanel.revalidate();
            dashRecentActivityPanel.repaint();
        }

        // 7. Update Expense Screen Total Card
        if (expenseTotalCardLabel != null) {
            expenseTotalCardLabel.setText("\u20B9" + currencyFormat.format(totalExpense));
        }

        // 8. Update Progress Screen Widgets
        if (progOverallLabel != null) {
            progOverallLabel.setText(overallScore + "%");
            progOverallBar.setValue(overallScore);

            progTasksDetailLabel.setText(completedTasks + " / " + totalTasks + " completed (" + taskPercent + "%)");
            progTasksBar.setValue(taskPercent);

            progGoalsDetailLabel.setText("Average Completion: " + goalPercent + "%");
            progGoalsBar.setValue(goalPercent);

            progHabitsDetailLabel.setText(completedHabits + " / " + totalHabits + " completed (" + habitPercent + "%)");
            progHabitsBar.setValue(habitPercent);

            progExpenseDetailLabel.setText("Total Spending: \u20B9" + currencyFormat.format(totalExpense));
        }
    }

    private JPanel createActivityItem(String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 6));
        item.setBackground(COLOR_CARD);
        JLabel bullet = new JLabel("\u2022");
        bullet.setForeground(COLOR_PRIMARY);
        bullet.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textLabel.setForeground(COLOR_TEXT_MAIN);

        item.add(bullet);
        item.add(textLabel);
        return item;
    }

    // =========================================================================
    // UI COMPONENT STYLING HELPERS
    // =========================================================================

    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(COLOR_CARD);
        Border lineBorder = BorderFactory.createLineBorder(COLOR_BORDER, 1);
        Border padding = new EmptyBorder(16, 18, 16, 18);
        card.setBorder(new CompoundBorder(lineBorder, padding));
        return card;
    }

    private JButton createStyledButton(String text, final Color bgColor) {
        final JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(36);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(COLOR_TEXT_MAIN);
        table.setSelectionBackground(new Color(0xEE, 0xF2, 0xFF));
        table.setSelectionForeground(COLOR_PRIMARY);
        table.setShowGrid(true);
        table.setGridColor(COLOR_BORDER);
        table.setFillsViewportHeight(true);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(0xFA, 0xFA, 0xFA));
        header.setForeground(COLOR_TEXT_SECONDARY);
        header.setPreferredSize(new Dimension(100, 36));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}
