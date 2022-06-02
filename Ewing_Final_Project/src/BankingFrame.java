/** S. Ewing, 2022, All rights reserved. 
  */ 

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BankingFrame extends JFrame {
    
    private static BankingDB bankingDB = new BankingDB();

    private List<Customer> customers;
    private List<Account> accounts;
    private JTextField searchFirstNameField;
    private JTextField searchLastNameField;
    private JList searchResultList;
    private DefaultListModel<String> resultsModel;
    private JTextField interestMonthField;
    private JTextField calculatedInterestField;
    private JTextField customerIDField;
    private JTextField customerFirstNameField;
    private JTextField customerLastNameField;
    private JTextField addressField;
    private JTextField phoneNumberField;
    private JTextField accountNumberField;
    private JTextField interestRateField;
    private JTextField balanceField;
    private JTextField withdrawDepositField;
    

    public BankingFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        initComponents();
    }

    private void initComponents() {
        setTitle("Banking Application");
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        searchFirstNameField = new JTextField();
        searchLastNameField = new JTextField();
        Dimension dim = new Dimension(250, 20);
        searchFirstNameField.setPreferredSize(new Dimension(dim));
        searchFirstNameField.setMinimumSize(new Dimension(dim));
        searchLastNameField.setPreferredSize(new Dimension(dim));
        searchLastNameField.setMinimumSize(new Dimension(dim));
        
        JButton searchCustomerButton = new JButton("Search");
        searchCustomerButton.addActionListener(event -> searchCustomerButtonClicked());
        JButton viewAllCustomersButton = new JButton("View All Customers");
        viewAllCustomersButton.addActionListener(event -> viewAllCustomersButtonClicked());
        
        resultsModel = new DefaultListModel<>();
        searchResultList = new JList(resultsModel);
        searchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane resultsScrollPane = new JScrollPane(searchResultList);
        
        JButton viewCustomerAccountButton = new JButton("View Customer Account");
        viewCustomerAccountButton.addActionListener(event -> viewCustomerAccountButtonClicked());
        
        JButton addNewCustomerButton = new JButton("Add New Customer");
        addNewCustomerButton.addActionListener(event -> addNewCustomerButtonClicked());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout()); 
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        topPanel.add(new JLabel("First Name"), getConstraints(0, 0));
        topPanel.add(searchFirstNameField, getConstraints(1, 0));
        topPanel.add(new JLabel("Last Name"), getConstraints(0, 1));
        topPanel.add(searchLastNameField, getConstraints(1, 1));
        topPanel.add(searchCustomerButton, getConstraints(2, 0));
        topPanel.add(viewAllCustomersButton, getConstraints(0, 2, 3, 1));
        topPanel.add(resultsScrollPane, getConstraints(0, 3, 2, 2));
        topPanel.add(viewCustomerAccountButton, getConstraints(0, 5));
        topPanel.add(addNewCustomerButton, getConstraints(1, 5));
       
        customerIDField = new JTextField();
        customerFirstNameField = new JTextField();
        customerLastNameField = new JTextField();
        addressField = new JTextField();
        phoneNumberField = new JTextField();
        accountNumberField = new JTextField();
        interestRateField = new JTextField();
        balanceField = new JTextField();
        withdrawDepositField = new JTextField();
        
        customerIDField.setPreferredSize(new Dimension(50, 20));
        customerIDField.setMinimumSize(new Dimension(50, 20));
        customerIDField.setEnabled(false);
        customerFirstNameField.setPreferredSize(dim);
        customerFirstNameField.setMinimumSize(dim);
        customerFirstNameField.setEnabled(false);        
        customerLastNameField.setPreferredSize(dim);
        customerLastNameField.setMinimumSize(dim); 
        customerLastNameField.setEnabled(false);
        addressField.setPreferredSize(dim);
        addressField.setMinimumSize(dim);  
        addressField.setEnabled(false);
        phoneNumberField.setPreferredSize(dim);
        phoneNumberField.setMinimumSize(dim);       
        phoneNumberField.setEnabled(false);
        JButton openAccountButton = new JButton("Open New Account");
        openAccountButton.addActionListener(event -> openAccountButtonClicked());
        JButton addExistingAccountButton = new JButton("Add to Existing Account");
        addExistingAccountButton.addActionListener(event -> addExistingAccountButtonClicked());
        accountNumberField.setPreferredSize(dim);
        accountNumberField.setMinimumSize(dim);     
        accountNumberField.setEnabled(false);
        interestRateField.setPreferredSize(dim);
        interestRateField.setMinimumSize(dim);     
        interestRateField.setEnabled(false);
        balanceField.setPreferredSize(dim);
        balanceField.setMinimumSize(dim); 
        balanceField.setEnabled(false);
        withdrawDepositField.setPreferredSize(dim);
        withdrawDepositField.setMinimumSize(dim);
        withdrawDepositField.setEnabled(false);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(event -> withdrawButtonClicked());
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(event -> depositButtonClicked());

        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout()); 
        centerPanel.add(new JLabel("Customer ID"), getConstraints(0, 0));
        centerPanel.add(customerIDField, getConstraints(1, 0, 2, 1));
        centerPanel.add(new JLabel("Customer First Name"), getConstraints(0, 1));
        centerPanel.add(customerFirstNameField, getConstraints(1, 1, 2, 1));
        centerPanel.add(new JLabel("Customer Last Name"), getConstraints(0, 2));
        centerPanel.add(customerLastNameField, getConstraints(1, 2, 2, 1));        
        centerPanel.add(new JLabel("Address"), getConstraints(0, 3));
        centerPanel.add(addressField, getConstraints(1, 3, 2, 1));
        centerPanel.add(new JLabel("Phone Number"), getConstraints(0, 4));
        centerPanel.add(phoneNumberField, getConstraints(1, 4, 2, 1));
        centerPanel.add(openAccountButton, getConstraints(1, 5));
        centerPanel.add(addExistingAccountButton, getConstraints(2, 5));
        centerPanel.add(new JLabel("Account Number"), getConstraints(0, 6));
        centerPanel.add(accountNumberField, getConstraints(1, 6, 2, 1)); 
        centerPanel.add(new JLabel("Interest Rate"), getConstraints(0, 7));
        centerPanel.add(interestRateField, getConstraints(1, 7, 2, 1)); 
        centerPanel.add(new JLabel("Balance"), getConstraints(0, 8));
        centerPanel.add(balanceField, getConstraints(1, 8, 2, 1));
        centerPanel.add(new JLabel("Withdraw / Deposit"), getConstraints(0, 9));
        centerPanel.add(withdrawDepositField, getConstraints(1, 9, 2, 1));
        centerPanel.add(withdrawButton, getConstraints(1, 10));
        centerPanel.add(depositButton, getConstraints(2, 10));
        


        JButton previousCustomerButton = new JButton("Previous Customer");
        previousCustomerButton.addActionListener(event -> previousCustomerButtonClicked());
        JButton nextCustomerButton = new JButton("Next Customer");
        nextCustomerButton.addActionListener(event -> nextCustomerButtonClicked());
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(event -> addCustomerButtonClicked());
        JButton updateCustomerButton = new JButton("Update Customer");
        updateCustomerButton.addActionListener(event -> updateCustomerButtonClicked());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(previousCustomerButton, getConstraints(0, 0));
        buttonPanel.add(nextCustomerButton, getConstraints(0, 1));
        buttonPanel.add(addCustomerButton, getConstraints(0, 2));
        buttonPanel.add(updateCustomerButton, getConstraints(0, 3));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 4));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 5));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 6));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 7));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 8));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 9));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 10));
        buttonPanel.add(new JLabel(" "), getConstraints(0, 11));
        

        
        JPanel bottomPanel = new JPanel();
        interestMonthField = new JTextField();
        calculatedInterestField = new JTextField();
        interestMonthField.setPreferredSize(new Dimension(50, 20));
        interestMonthField.setMinimumSize(new Dimension(50, 20));
        interestMonthField.setEditable(false);
        calculatedInterestField.setPreferredSize(new Dimension(100, 20));
        calculatedInterestField.setMinimumSize(new Dimension(100, 20));
        calculatedInterestField.setEditable(false);
        JButton calculateInterestButton = new JButton("Calculate Interest");
        calculateInterestButton.addActionListener(event -> calculateInterestButtonClicked());
        
        bottomPanel.add(new JLabel("Interest Month"));
        bottomPanel.add(interestMonthField);
        bottomPanel.add(new JLabel("Calculated Interest"));
        bottomPanel.add(calculatedInterestField);
        bottomPanel.add(calculateInterestButton);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        
        pack();
    }

     // helper method for getting a GridBagConstraints object
    private GridBagConstraints getConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        return c;
    }
    
    private GridBagConstraints getConstraints(int x, int y, int width, int height) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.gridheight = height;
        return c;
    }
 
    
private void searchCustomerButtonClicked() {
    customers = bankingDB.getAllCustomers();
    List<Customer> results = new ArrayList<>();
    Customer c;
    
    if (customers == null) {
        JOptionPane.showMessageDialog(this, "No Customer Found.  Please add new customer.");
    } else {
        for (Customer customer : customers) {
            if (customer.getFirstName().equalsIgnoreCase(searchFirstNameField.getText()) &&
                customer.getLastName().equalsIgnoreCase(searchLastNameField.getText())) {
                results.add(customer);
            }
        }
        if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer Not Found");
        } else {
            resultsModel.clear();
            for (Customer result : results) {
                String output = "";
                output += result.getCustomerID();
                output += ". ";
                output += result.getFirstName();
                output += " ";
                output += result.getLastName();
                output += " | ";
                output += result.getPhoneNumber();
                resultsModel.addElement(output);
                searchResultList.ensureIndexIsVisible(resultsModel.size());
            }   
        }        
    }


}

    private void viewAllCustomersButtonClicked() {
        customers = bankingDB.getAllCustomers();
        Customer c;
        if (customers == null) {
            JOptionPane.showMessageDialog(this, "No Customers Found.  Please add new customer.");
        } else {
            resultsModel.clear();
            for (int i = 0; i < customers.size(); i++) {
                String output = "";
                c = customers.get(i);
                output += c.getCustomerID();
                output += ". ";
                output += c.getFirstName();
                output += " ";
                output += c.getLastName();
                output += " | ";
                output += c.getPhoneNumber();
                resultsModel.addElement(output);
                searchResultList.ensureIndexIsVisible(i);
            }
        }
    }

    private void viewCustomerAccountButtonClicked() {
        if (searchResultList.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer before "
                    + "using this button.");
            return;
        }
        customerFirstNameField.setEnabled(true);
        customerLastNameField.setEnabled(true);
        addressField.setEnabled(true);
        phoneNumberField.setEnabled(true);
        withdrawDepositField.setEnabled(true);
        String selection = searchResultList.getSelectedValue().toString();
        String[] s = selection.split("\\.");
        int i = Integer.parseInt(s[0]);
        customers = bankingDB.getAllCustomers();
        Customer c = null;
        for (Customer customer : customers) {
            if (customer.getCustomerID() == i) {
                c = customer;
            } 
        }

        customerIDField.setText(String.valueOf(c.getCustomerID()));
        customerFirstNameField.setText(c.getFirstName());
        customerLastNameField.setText(c.getLastName());
        addressField.setText(c.getAddress());
        phoneNumberField.setText(c.getPhoneNumber());
        interestMonthField.setText("");
        calculatedInterestField.setText(""); 
        if (c.getAccountID() == 0) {
            accounts = bankingDB.getAllAccounts();
            accountNumberField.setText("");
            interestRateField.setText("");
            balanceField.setText("");
        } else {
            accountNumberField.setText(String.valueOf(c.getAccountID()));
            accounts = bankingDB.getAllAccounts();
            for (Account a : accounts) {
                if (a.getAccountID() == c.getAccountID()) {
                    NumberFormat percent = NumberFormat.getPercentInstance();
                    percent.setMaximumFractionDigits(2);
                    interestRateField.setText(String.valueOf(percent.format(a.getInterestRate()/100)));
                    NumberFormat currency = NumberFormat.getCurrencyInstance();
                    balanceField.setText(String.valueOf(currency.format(a.getBalance())));                    
                }
            }
        }
        searchResultList.clearSelection();
    }
    
    private void addNewCustomerButtonClicked() {
        customerIDField.setText("");
        customerFirstNameField.setEnabled(true);
        customerFirstNameField.setText("");
        customerLastNameField.setEnabled(true);
        customerLastNameField.setText("");
        addressField.setEnabled(true);
        addressField.setText("");
        phoneNumberField.setEnabled(true);
        phoneNumberField.setText("");
        accountNumberField.setText("");
        interestRateField.setText("");
        balanceField.setText("");
        withdrawDepositField.setEnabled(true);
        withdrawDepositField.setText("");
        interestMonthField.setText("");
        calculatedInterestField.setText("");
        searchResultList.clearSelection();
    }
    
    private void previousCustomerButtonClicked() {
        if (customerIDField.getText().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please select a customer before "
                    + "using this button.");
        } else {
            int i = Integer.parseInt(customerIDField.getText());
            Customer customer = null;
            for (Customer c : customers) {
                if (c.getCustomerID() == i) {
                    customer = c;
                }
            }
            int oldIndex = customers.indexOf(customer);
            if (oldIndex == 0) {
                JOptionPane.showMessageDialog(this, "Beginning of List.");
            } else {
                Customer previousCustomer = customers.get(oldIndex-1);
                customerIDField.setText(String.valueOf(previousCustomer.getCustomerID()));
                customerFirstNameField.setText(previousCustomer.getFirstName());
                customerLastNameField.setText(previousCustomer.getLastName());
                addressField.setText(previousCustomer.getAddress());
                phoneNumberField.setText(previousCustomer.getPhoneNumber());
                interestMonthField.setText("");
                calculatedInterestField.setText(""); 
                if (previousCustomer.getAccountID() == 0) {
                    accountNumberField.setText("");
                    interestRateField.setText("");
                    balanceField.setText("");
                } else {
                    for (Account a : accounts) {
                        if (previousCustomer.getAccountID() == a.getAccountID()) {
                            accountNumberField.setText(String.valueOf(a.getAccountID()));
                            NumberFormat percent = NumberFormat.getPercentInstance();
                            percent.setMaximumFractionDigits(2);
                            interestRateField.setText(String.valueOf(percent.format(a.getInterestRate()/100)));
                            NumberFormat currency = NumberFormat.getCurrencyInstance();
                            balanceField.setText(String.valueOf(currency.format(a.getBalance())));
                        }
                    }
                }

            }
        }
    }
    
    private void nextCustomerButtonClicked() {
        if (customerIDField.getText().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please select a customer before "
                    + "using this button.");
        } else {
            int i = Integer.parseInt(customerIDField.getText());
            Customer customer = null;
            for (Customer c : customers) {
                if (c.getCustomerID() == i) {
                    customer = c;
                }
            }
            int oldIndex = customers.indexOf(customer);
            if (oldIndex == customers.size()-1) {
                JOptionPane.showMessageDialog(this, "End of List.");
            } else {
                Customer nextCustomer = customers.get(oldIndex+1);
                customerIDField.setText(String.valueOf(nextCustomer.getCustomerID()));
                customerFirstNameField.setText(nextCustomer.getFirstName());
                customerLastNameField.setText(nextCustomer.getLastName());
                addressField.setText(nextCustomer.getAddress());
                phoneNumberField.setText(nextCustomer.getPhoneNumber());
                interestMonthField.setText("");
                calculatedInterestField.setText(""); 
                if (nextCustomer.getAccountID() == 0) {
                    accountNumberField.setText("");
                    interestRateField.setText("");
                    balanceField.setText("");
                } else {
                    for (Account a : accounts) {
                        if (nextCustomer.getAccountID() == a.getAccountID()) {
                            accountNumberField.setText(String.valueOf(a.getAccountID()));
                            NumberFormat percent = NumberFormat.getPercentInstance();
                            percent.setMaximumFractionDigits(2);
                            interestRateField.setText(String.valueOf(percent.format(a.getInterestRate()/100)));
                            NumberFormat currency = NumberFormat.getCurrencyInstance();
                            balanceField.setText(String.valueOf(currency.format(a.getBalance())));
                        }
                    }
                }
            }

        }
    }
    
    private void addCustomerButtonClicked() {
        if (customerFirstNameField.getText().equals("") ||
            customerLastNameField.getText().equals("") ||
            addressField.getText().equals("") ||
            phoneNumberField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
        }
        
        if (customers != null) {
            for (Customer c : customers) {
                if (c.getFirstName().equals(customerFirstNameField.getText()) &&
                    c.getLastName().equals(customerLastNameField.getText()) &&
                    c.getAddress().equals(addressField.getText()) &&
                    c.getPhoneNumber().equals(phoneNumberField.getText())) {
                        JOptionPane.showMessageDialog(this, "Customer Already Exists.");
                        return;
                    }
            }
        } 
               
        Customer customer = new Customer(0, customerFirstNameField.getText(),
                customerLastNameField.getText(),
                addressField.getText(),
                phoneNumberField.getText(), 0);
        boolean success = bankingDB.addCustomer(customer);
        if (success) {
            JOptionPane.showMessageDialog(this, customer.getName() +
                    " has been successfully added.");
        }
        customers = bankingDB.getAllCustomers();
        accounts = bankingDB.getAllAccounts();
        for (Customer c : customers) {
            if (c.getFirstName().equals(customerFirstNameField.getText()) &&
                c.getLastName().equals(customerLastNameField.getText()) &&
                c.getAddress().equals(addressField.getText()) &&
                c.getPhoneNumber().equals(phoneNumberField.getText())) {
                    customerIDField.setText(Integer.toString(c.getCustomerID()));
                }
        }
    }
    
    private void updateCustomerButtonClicked() {
        if (customers != null) {
            for (Customer c : customers) {
                if (c.getFirstName().equals(customerFirstNameField.getText()) &&
                    c.getLastName().equals(customerLastNameField.getText()) &&
                    c.getAddress().equals(addressField.getText()) &&
                    c.getPhoneNumber().equals(phoneNumberField.getText())) {
                        JOptionPane.showMessageDialog(this, "No Changes Found.  Please try again.");
                        return;
                    }
            }
        }
            
        if (customerFirstNameField.getText().equals("") ||
            customerLastNameField.getText().equals("") ||
            addressField.getText().equals("") ||
            phoneNumberField.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
        }
        
        if (customerIDField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Can't update new customer. "
                    + "Please use 'Add Customer' button");
            return;
        }
        
        for (Customer c: customers) {
            if (c.getCustomerID() == Integer.parseInt(customerIDField.getText())) {
                c.setFirstName(customerFirstNameField.getText());
                c.setLastName(customerLastNameField.getText());
                c.setAddress(addressField.getText());
                c.setPhoneNumber(phoneNumberField.getText());
                bankingDB.updateCustomer(c);
                JOptionPane.showMessageDialog(this, c.getName() + " has been "
                    + "successfully updated.");
            }
        }
    }
    
    private void openAccountButtonClicked() {
        if (accountNumberField.getText().equals(0) || !accountNumberField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Error!  Customer "
                + "already has an account."); 
            return;
        } else if (customerIDField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please select a customer before "
                + "using this button.");
            return;
        }
        String strInterestRate = "";
        Double doubleInterestRate = 0.0;
        boolean success = false;
        while (success == false) {
            strInterestRate = JOptionPane.showInputDialog(this,
                            "Please enter Account Interest Rate", null);
            if (strInterestRate == null) {
                return;
            } else if (strInterestRate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Interest Rate cannot be left blank.");
            } else {
                try {
                    doubleInterestRate = Double.parseDouble(strInterestRate);
                    success = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Interest Rate must be a valid number.");
                    success = false;   
                } 
            }            
        }
       
        String strBalance = "";
        Double doubleBalance = 0.0;
        success = false;
        while (success == false) {
            strBalance = JOptionPane.showInputDialog(this,
                    "Please enter Account Opening Balance", null);
            if (strBalance == null) {
                return;
            } else if (strBalance.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Balance cannot be left blank.");
            } else {
                try {
                    doubleBalance = Double.parseDouble(strBalance);
                    success = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Balance must be a valid number.");
                    success = false;   
                } 
            }
        }

        Account account = new Account(0, doubleInterestRate, doubleBalance);
        bankingDB.addAccount(account);
        accounts = bankingDB.getAllAccounts();
        Account newestAccount = accounts.get(accounts.size()-1);
        accountNumberField.setText(Integer.toString(newestAccount.getAccountID()));
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        interestRateField.setText(String.valueOf(percent.format(newestAccount.getInterestRate()/100)));
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        balanceField.setText(String.valueOf(currency.format(newestAccount.getBalance())));
        int i = Integer.parseInt(customerIDField.getText());
            Customer c = null;
            for (Customer customer : customers) {
                if (customer.getCustomerID() == i) {
                    c = customer;
                }
            }
        c.setAccountID(newestAccount.getAccountID());
        bankingDB.updateCustomer(c);
        JOptionPane.showMessageDialog(this, "Account for " + c.getName()
        + " has been successfully added"); 
    }
    
    private void addExistingAccountButtonClicked() {
        if (accountNumberField.getText().equals(0) || !accountNumberField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Error!  Customer "
                + "already has an account."); 
            return;
        } else if (customerIDField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please select a customer before "
                + "using this button.");
            return;
        }
        
        String strAccountNumber = "";
        boolean success = false;
        while (success == false) {
            strAccountNumber = JOptionPane.showInputDialog(this,
                "Please enter the existing account number to add", null);
            if (strAccountNumber == null) {
                return;
            } else if (strAccountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Account Number cannot be "
                        + "left blank.");
            } else {
                int intAccountNumber = Integer.parseInt(strAccountNumber);
                int count = 0;
                for (Account a : accounts) {
                    if (intAccountNumber == a.getAccountID()) {
                        int i = Integer.parseInt(customerIDField.getText());
                        Customer c = null;
                        for (Customer customer : customers) {
                            if (customer.getCustomerID() == i) {
                                c = customer;
                            }
                        }
                    c.setAccountID(intAccountNumber);
                    bankingDB.updateCustomer(c);
                    Account account = null;
                    for (Account a2 : accounts) {
                        if (a2.getAccountID() == intAccountNumber) {
                            accountNumberField.setText(strAccountNumber);
                            NumberFormat percent = NumberFormat.getPercentInstance();
                            percent.setMaximumFractionDigits(2);
                            interestRateField.setText(percent.format(a2.getInterestRate()/100));
                            NumberFormat currency = NumberFormat.getCurrencyInstance();
                            balanceField.setText(currency.format(a2.getBalance()));                        
                            JOptionPane.showMessageDialog(this, "Existing account "
                                + "successfully added.");
                        }
                    }
                    
                    
                    success = true;    
                    } else {
                        count += 1;
                        
                    } 
                }
                if (count == accounts.size()) {
                    JOptionPane.showMessageDialog(this, "Account not found. "
                            + "Please try again.");
                }
            }
        }
    }
    
    private void withdrawButtonClicked() {
        if (accountNumberField.getText().isEmpty() && customerIDField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        } else if (accountNumberField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please open an account first.");
            return;
        } else if (withdrawDepositField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a withdraw amount.");
            return;
        } else {
            for (Account a: accounts) {
                if (a.getAccountID() == Integer.parseInt(accountNumberField.getText())) {
                    Double withdrawAmount = 0.0;
                    try {
                        withdrawAmount = Double.parseDouble(withdrawDepositField.getText());
                        double newBalance = a.getBalance() - withdrawAmount;
                        if (withdrawAmount < 0) {
                            withdrawDepositField.setText("");
                            JOptionPane.showMessageDialog(this, "Amount must be greater than zero.");
                           return;
                        }
                        if (newBalance < 0) {
                            withdrawDepositField.setText("");
                            JOptionPane.showMessageDialog(this, "Insufficient Funds.");
                            return;
                        } else {
                            a.setBalance(newBalance);
                            boolean success = bankingDB.updateAccount(a);
                            if (success) {
                                NumberFormat currency = NumberFormat.getCurrencyInstance();
                                balanceField.setText(String.valueOf(currency.format(a.getBalance())));
                                withdrawDepositField.setText("");
                                JOptionPane.showMessageDialog(this, "Withdraw was successful.");
                            }
                        }                        
                    } catch (NumberFormatException e) {
                        withdrawDepositField.setText("");
                        JOptionPane.showMessageDialog(this, "Withdraw amount must be a valid number.");
                        return;
                    }
                }
            }
        }        
    }
    
    private void depositButtonClicked()  {
        if (accountNumberField.getText().isEmpty() && customerIDField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        } else if (accountNumberField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please open an account first.");
            return;
        } else if (withdrawDepositField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter a deposit amount.");
            return;
        } else {
            for (Account a: accounts) {
                if (a.getAccountID() == Integer.parseInt(accountNumberField.getText())) {
                    Double depositAmount = 0.0;
                    try {
                        depositAmount = Double.parseDouble(withdrawDepositField.getText());
                        double newBalance = a.getBalance() + depositAmount;
                        if (depositAmount < 0) {
                            withdrawDepositField.setText("");
                            JOptionPane.showMessageDialog(this, "Amount must be greater than zero.");
                           return;
                        }                        
                        if (newBalance < 0) {
                            withdrawDepositField.setText("");
                            JOptionPane.showMessageDialog(this, "Insufficient Funds.");
                        } else {
                            a.setBalance(newBalance);
                            boolean success = bankingDB.updateAccount(a);
                            if (success) {
                                NumberFormat currency = NumberFormat.getCurrencyInstance();
                                balanceField.setText(String.valueOf(currency.format(a.getBalance())));
                                withdrawDepositField.setText("");
                                JOptionPane.showMessageDialog(this, "Deposit was successful.");
                            }
                        }                        
                    } catch (NumberFormatException e) {
                        withdrawDepositField.setText("");
                        JOptionPane.showMessageDialog(this, "Deposit amount must be a valid number.");
                        return;
                    }
                }
            }
        }        
    }
    
    private void calculateInterestButtonClicked() {
        if (accountNumberField.getText().isEmpty() && customerIDField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        } else if (accountNumberField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please open an account first.");
            return;
        } else {
            Object[] months = {"01-Jan", "02-Feb", "03-Mar",
                "04-Apr", "05-May", "06-Jun", "07-Jul", "08-Aug",
                "09-Sep", "10-Oct", "11-Nov", "12-Dec"};
            String s = (String)JOptionPane.showInputDialog(this,
                                "Please enter the current month\n"
                                + "to calculate the interest accrued\n"
                                + "so far this year.",
                                "Customized Dialog",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                months,
                                "01-Jan");
            if (s == null) {
                return;
            } else if ((s != null) && (s.length() > 0)) {
                int month = 0;
                if (s.substring(0, 2).equals("01")) {
                    month = 1;
                } else if (s.substring(0, 2).equals("02")) {
                    month = 2;
                } else if (s.substring(0, 2).equals("03")) {
                    month = 3;
                } else if (s.substring(0, 2).equals("04")) {
                    month = 4;
                } else if (s.substring(0, 2).equals("05")) {
                    month = 5;
                } else if (s.substring(0, 2).equals("06")) {
                    month = 6;
                } else if (s.substring(0, 2).equals("07")) {
                    month = 7;
                } else if (s.substring(0, 2).equals("08")) {
                    month = 8;
                } else if (s.substring(0, 2).equals("09")) {
                    month = 9;
                } else if (s.substring(0, 2).equals("10")) {
                    month = 10;
                } else if (s.substring(0, 2).equals("11")) {
                    month = 11;
                } else if (s.substring(0, 2).equals("12")) {
                    month = 12;
                }

                int i = Integer.parseInt(accountNumberField.getText());
                for (Account a : accounts) {
                    if (a.getAccountID() == i) {
                        BigDecimal monthNumber = new BigDecimal(month);
                        BigDecimal hundred = new BigDecimal("100");
                        BigDecimal twelve = new BigDecimal("12");
                        BigDecimal balance = new BigDecimal(Double.toString(a.getBalance()));
                        BigDecimal interestRate = new BigDecimal(Double.toString(a.getInterestRate()));
                        BigDecimal adjustedInterestRate = interestRate.divide(hundred, 5, RoundingMode.HALF_UP);
                        BigDecimal monthlyInterestRate = adjustedInterestRate.divide(twelve, 10, RoundingMode.HALF_UP);
                        BigDecimal monthlyinterestAccrued = balance.multiply(monthlyInterestRate).setScale(10, RoundingMode.HALF_UP);
                        BigDecimal calculatedAccruedInterest = monthlyinterestAccrued.multiply(monthNumber).setScale(2, RoundingMode.HALF_UP);
                        NumberFormat currency = NumberFormat.getCurrencyInstance();
                        interestMonthField.setText(s.substring(3));
                        calculatedInterestField.setText(String.valueOf(currency.format(calculatedAccruedInterest))); 
                 
                    }
                }
            }            
        }
    }
               
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BankingFrame frame = new BankingFrame();
            frame.setVisible(true);
        });
    }
}