# Personal Productivity Dashboard 🚀

A clean, modern, and offline desktop application built in Java Swing for students and professionals to manage daily tasks, track long-term goals, build healthy habits, monitor expenses, and visualize overall productivity.

---

## 📌 Project Overview

This project is a **college-level Java project** designed for second-year Computer Science and Engineering (CSE) students. It illustrates foundational Software Engineering and Java concepts with crystal-clear, beginner-friendly code while maintaining a polished, professional user interface.

### Key Pillars Demonstrated:
1. **Java Object-Oriented Programming (OOP)** — Clean class definitions for real-world entities.
2. **Encapsulation** — Private attributes protected with public getters and setters.
3. **Java Collections Framework** — `ArrayList<T>` for dynamic in-memory data structures.
4. **File Handling (I/O)** — Persistent pipe-delimited storage using `BufferedReader` and `BufferedWriter`.
5. **Java Swing GUI** — Event-driven graphical interface with responsive navigation.
6. **Robust Exception Handling** — Graceful input validation and `try-catch` error trapping.

---

## 🗂 Project Directory Structure

```text
PersonalProductivityDashboard/
│
├── src/
│   ├── Main.java                 # Entry point, sets Look & Feel and launches GUI
│   ├── Task.java                 # Task model (name, priority, completed)
│   ├── Goal.java                 # Goal model (name, target, progress, percentage)
│   ├── Habit.java                # Habit model (name, completed)
│   ├── Expense.java              # Expense model (description, category, amount)
│   ├── ProductivityManager.java  # Controller managing ArrayList collections
│   ├── FileManager.java          # File I/O reading/writing to text files
│   └── DashboardGUI.java         # Swing user interface & screens
│
├── data/
│   ├── tasks.txt                 # Pipe-delimited tasks storage
│   ├── goals.txt                 # Pipe-delimited goals storage
│   ├── habits.txt                # Pipe-delimited habits storage
│   └── expenses.txt              # Pipe-delimited expenses storage
│
└── README.md                     # Project documentation & Viva reference
```

---

## ⚙️ Compilation & Running Instructions

### Prerequisites
- Java Development Kit (JDK 8 or higher, e.g. OpenJDK 17, Oracle JDK)

### 1. Open Terminal or PowerShell
Navigate into the project root directory:
```bash
cd PersonalProductivityDashboard
```

### 2. Compile Java Source Files
Compile all `.java` files from `src/` into the `bin/` directory:
```bash
javac -d bin src/*.java
```

### 3. Run the Application
Execute the compiled `Main` class:
```bash
java -cp bin Main
```

*(Note: On Windows PowerShell, make sure you execute the command from the `PersonalProductivityDashboard` folder so the application locates the `data/` folder directly).*

---

## 🎨 User Interface & Color Scheme

The application follows a clean, professional aesthetic:
- **Background**: `#F5F7FA` (Soft neutral gray)
- **Sidebar**: `#172033` (Deep midnight navy)
- **Primary Accent**: `#4F46E5` (Indigo)
- **Cards**: `#FFFFFF` (Clean white with subtle borders)
- **Main Text**: `#1F2937` (Dark charcoal)
- **Secondary Text**: `#6B7280` (Muted gray)
- **Success / Completed**: `#22C55E` (Emerald green)
- **Warning / Milestones**: `#F59E0B` (Amber)
- **Danger / Deletions / Expenses**: `#EF4444` (Coral red)

---

## 💡 Viva Voce & Technical Examination Guide

When presenting this project to examiners or professors, use this quick reference:

### 1. Object-Oriented Programming (OOP)
- **Separate Entity Classes**: The application models distinct real-world domain concepts through `Task`, `Goal`, `Habit`, and `Expense` classes.
- **Separation of Concerns**: Domain data models hold data and entity-specific logic, `ProductivityManager` manages business rules and collections, `FileManager` handles persistence, and `DashboardGUI` renders the view.

### 2. Encapsulation
- All instance variables in `Task`, `Goal`, `Habit`, and `Expense` are declared with `private` access modifiers.
- Access to these variables is strictly governed through `public` getters (`getName()`, `getPriority()`, `getTarget()`, etc.) and controlled business mutators (`updateProgress()`, `markCompleted()`).
- In `Goal.java`, `updateProgress()` enforces business validation preventing progress from exceeding the target or becoming negative.

### 3. Java Collections (`ArrayList`)
- The `ProductivityManager` uses `ArrayList<Task>`, `ArrayList<Goal>`, `ArrayList<Habit>`, and `ArrayList<Expense>`.
- **Why ArrayList?** Unlike fixed-size arrays, `ArrayList` dynamically resizes as users add and delete tasks, goals, habits, and expenses at runtime.
- Traversal is performed using straightforward `for` loops without complex streams to guarantee readability.

### 4. File Handling & Persistence
- Data is saved in lightweight flat files under `data/`:
  - `tasks.txt`: `Task Name|Priority|Status`
  - `goals.txt`: `Goal Name|Target|Progress`
  - `habits.txt`: `Habit Name|Status`
  - `expenses.txt`: `Description|Category|Amount`
- `FileManager.java` uses `BufferedWriter` with `FileWriter` for writing, and `BufferedReader` with `FileReader` for line-by-line reading.
- A `finally` block or try-with-resources safely closes all file streams to prevent file handle leaks.

### 5. Exception Handling
- `try-catch` blocks protect the user experience from unexpected input:
  - `NumberFormatException` is caught when parsing goal targets, progress integers, and expense amounts (`Double.parseDouble`, `Integer.parseInt`).
  - `IOException` is trapped during file reading and writing.
  - Empty text field validation prevents blank items from being submitted.
- Informative `JOptionPane` dialogs notify the user when input is invalid without crashing the program.

### 6. Swing GUI Architecture
- Uses `JFrame` as the root window.
- Organizes the layout with `BorderLayout`:
  - `WEST`: Fixed-width dark navigation sidebar with styled `JButton` elements.
  - `CENTER`: `CardLayout` container that dynamically toggles between Dashboard, Tasks, Goals, Habits, Expenses, Progress, Hank, and Settings screens.
- Utilizes `JTable` with custom `DefaultTableModel` and centered cell renderers for structured data presentation.
- Utilizes `JProgressBar` to visually depict percentages for tasks, habits, and goals.

---

## 📊 Progress Calculation Formula

The application calculates progress using simple arithmetic:

$$\text{Task Progress} = \frac{\text{Completed Tasks}}{\text{Total Tasks}} \times 100$$

$$\text{Habit Progress} = \frac{\text{Completed Habits}}{\text{Total Habits}} \times 100$$

$$\text{Goal Progress} = \frac{1}{N} \sum_{i=1}^{N} \left(\frac{\text{Progress}_i}{\text{Target}_i} \times 100\right)$$

$$\text{Overall Progress} = \frac{\text{Task Progress} + \text{Habit Progress} + \text{Goal Progress}}{\text{Number of Active Categories}}$$

---

## 🤖 Future Innovation: Hank – AI Productivity Assistant

```text
Future Enhancement:

Hank will be enhanced with an AI model using
Retrieval-Augmented Generation (RAG) and a
Large Language Model (LLM).

The AI will analyze the user's existing
productivity data and provide personalized
recommendations.

The AI is an additional innovation and is not
part of the core Java implementation.
```

### Architectural Roadmap for Hank:
- **Core Java Base (Current)**: 100% offline, zero external dependencies, robust and fast.
- **Planned Extension (Future)**:
  - Connect a local or cloud LLM endpoint (e.g. Gemini API / Ollama).
  - RAG Pipeline: Vectorize user's historical task completions, habit streaks, and expense patterns.
  - Smart Assistant features: "Hank, suggest the best study schedule for today based on my pending DSA goals" or "Analyze where I am overspending this week".
"# Java-Personal-Productivity-dashboard" 
