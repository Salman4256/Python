import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Transaction {
    private String type;
    private String category;
    private double amount;
    private String date;

    public Transaction(String type, String category, double amount, String date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date; // Store the date as entered (in DD-MM-YYYY format)
    }

    @Override
    public String toString() {
        return type + " - " + category + " - ₹" + amount + " on " + date; // Show date in DD-MM-YYYY format
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

public class Demo1 extends Frame {
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private List transactionList = new List();
    private TextField typeField, categoryField, amountField, dateField;

    public Demo1() {
        setTitle("Budget Calculator");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Set background color of the main window
        setBackground(new Color(153, 153, 255)); // Light blue background

        // Top Panel for adding transactions
        Panel inputPanel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding for better spacing

        inputPanel.add(new Label("Type (income/expense):"), gbc);
        typeField = new TextField(20); // Width of 20 columns for better sizing
        gbc.gridx = 1;
        inputPanel.add(typeField, gbc);

        gbc.gridx = 0;
        inputPanel.add(new Label("Category:"), gbc);
        categoryField = new TextField(20); // Width of 20 columns for better sizing
        gbc.gridx = 1;
        inputPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        inputPanel.add(new Label("Amount:"), gbc);
        amountField = new TextField(20); // Width of 20 columns for better sizing
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        inputPanel.add(new Label("Date (DD-MM-YYYY):"), gbc);
        dateField = new TextField(20); // Width of 20 columns for better sizing
        gbc.gridx = 1;
        inputPanel.add(dateField, gbc);

        Button addButton = new Button("Add Transaction");
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Make the button span two columns
        inputPanel.add(addButton, gbc);
        addButton.addActionListener(e -> addTransaction());

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel for transaction list
        Panel listPanel = new Panel(new BorderLayout());
        listPanel.add(new Label("Transactions:"), BorderLayout.NORTH);

        // Set the background color of the transaction list
        transactionList.setBackground(new Color(204, 204, 255)); // Light yellow background
        listPanel.add(transactionList, BorderLayout.CENTER);

        add(listPanel, BorderLayout.CENTER);

        // Bottom Panel for summary and delete actions
        Panel actionPanel = new Panel();
        Button deleteButton = new Button("Delete Transaction");
        Button summaryButton = new Button("View Summary");
        actionPanel.add(deleteButton);
        actionPanel.add(summaryButton);

        deleteButton.addActionListener(e -> deleteTransaction());
        summaryButton.addActionListener(e -> showSummary());

        add(actionPanel, BorderLayout.SOUTH);

        // Window close event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    // Add a transaction
    private void addTransaction() {
        try {
            String type = typeField.getText();
            String category = categoryField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();

            // Simple validation to check if date is in correct format (DD-MM-YYYY)
            if (!isValidDate(date)) {
                showErrorDialog("Invalid date format. Please use DD-MM-YYYY format.");
                return;
            }

            Transaction transaction = new Transaction(type, category, amount, date);
            transactions.add(transaction);
            transactionList.add(transaction.toString());

            clearFields();
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid input. Please enter numeric values for amount.");
        }
    }

    // Delete a selected transaction
    private void deleteTransaction() {
        int selectedIndex = transactionList.getSelectedIndex();
        if (selectedIndex >= 0) {
            transactions.remove(selectedIndex);
            transactionList.remove(selectedIndex);
        } else {
            showErrorDialog("Please select a transaction to delete.");
        }
    }

    // Show summary with better alignment
    private void showSummary() {
        double totalIncome = 0;
        double totalExpenses = 0;

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("income")) {
                totalIncome += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("expense")) {
                totalExpenses += t.getAmount();
            }
        }

        double netSavings = totalIncome - totalExpenses;

        // Create a new dialog for the summary
        Dialog summaryDialog = new Dialog(this, "Summary", true);
        summaryDialog.setLayout(new GridLayout(4, 1, 10, 10)); // Grid layout with 4 rows

        // Set background color for summary dialog
        summaryDialog.setBackground(new Color(230, 230, 250)); // Light yellow background

        // Add labels to the summary dialog
        summaryDialog.add(new Label("Total Income: ₹" + totalIncome, Label.LEFT));
        summaryDialog.add(new Label("Total Expenses: ₹" + totalExpenses, Label.LEFT));
        summaryDialog.add(new Label("Net Savings: ₹" + netSavings, Label.LEFT));

        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> summaryDialog.dispose());
        summaryDialog.add(closeButton);

        summaryDialog.setSize(300, 200);
        summaryDialog.setVisible(true);
    }

    // Utility methods
    private void clearFields() {
        typeField.setText("");
        categoryField.setText("");
        amountField.setText("");
        dateField.setText("");
    }

    private void showErrorDialog(String message) {
        showInfoDialog("Error", message);
    }

    private void showInfoDialog(String title, String message) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new FlowLayout());
        dialog.setBackground(new Color(230, 230, 250)); // Light yellow background for the dialog
        dialog.add(new Label(message));
        Button okButton = new Button("OK");
        okButton.addActionListener(e -> dialog.dispose());
        dialog.add(okButton);
        dialog.setSize(350, 200); // Adjust dialog size
        dialog.setVisible(true);
    }

    // Helper method to validate date format (DD-MM-YYYY)
    private boolean isValidDate(String date) {
        String regex = "^([0-2][0-9]|(3)[0-1])-(0[1-9]|1[0-2])-(\\d{4})$";
        return date.matches(regex);
    }

    public static void main(String[] args) {
        new Demo1();
    }
}