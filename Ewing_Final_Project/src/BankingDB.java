/** S. Ewing, 2022, All rights reserved. 
  */  


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BankingDB {

        private Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:sqlite:BankingDB.sqlite";
        Connection connection = DriverManager.getConnection(dbUrl);
        return connection;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT CustomerID, FirstName, LastName,"
                   + "Address, PhoneNumber, AccountID "
                   + "FROM Customers ORDER BY LastName ASC";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cCustomerID = rs.getInt("CustomerID");
                String cFirstName = rs.getString("FirstName");
                String cLastName = rs.getString("LastName");
                String cAddress = rs.getString("Address");
                String cPhoneNumber = rs.getString("PhoneNumber");
                int cAccountID = rs.getInt("AccountID");
                Customer c = new Customer(cCustomerID, cFirstName, cLastName, cAddress, cPhoneNumber, cAccountID);
                customers.add(c);
            }
            return customers;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    public List<Customer> getCustomer(String firstName, String lastName) {
        String sql = "SELECT CustomerID, FirstName, LastName, "
                   + "Address, PhoneNumber, AccountID "
                   + "FROM Customers "
                   + "WHERE FirstName = ? AND LastName = ?";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cCustomerID = rs.getInt("CustomerID");
                String cFirstName = rs.getString("FirstName");
                String cLastName = rs.getString("LastName");
                String cAddress = rs.getString("Address");
                String cPhoneNumber = rs.getString("PhoneNumber");
                int cAccountID = rs.getInt("AccountID");
                Customer c = new Customer(cCustomerID, cFirstName, cLastName, cAddress, cPhoneNumber, cAccountID);
                customers.add(c);
            } 

            return customers;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    public boolean addCustomer(Customer c) {
        String sql = "INSERT INTO Customers (FirstName, LastName, "
                   +  "Address, PhoneNumber, AccountID ) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getPhoneNumber());
            ps.setInt(5, c.getAccountID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    
    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE Customers SET "
                   + "  FirstName = ?, "
                   + "  LastName = ?, "
                   + "  Address = ?, "
                   + "  PhoneNumber = ?, "
                   + "  AccountID = ? "
                   + "WHERE CustomerID = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getPhoneNumber());
            ps.setInt(5, c.getAccountID());
            ps.setInt(6, c.getCustomerID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public Account getAccount(int accountID) {
        String sql = "SELECT AccountID, InterestRate, Balance "
                   + "FROM Accounts "
                   + "WHERE AccountID = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double interestRate = rs.getDouble("InterestRate");
                double balance = rs.getDouble("Balance");
                Account a = new Account(accountID, interestRate, balance);
                rs.close();
                return a;
            } else {
                rs.close();
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }       
    }
    
    public List<Account> getAllAccounts() {
        String sql = "SELECT AccountID, InterestRate, Balance "
                   + "FROM Accounts ORDER BY AccountID ASC";
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int accountID = rs.getInt("AccountID");
                double interestRate = rs.getDouble("InterestRate");
                double balance = rs.getDouble("Balance");                
                Account a = new Account(accountID, interestRate, balance);
                accounts.add(a);
            }
            return accounts;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    } 
    
    public boolean addAccount(Account a) {
        String sql = "INSERT INTO Accounts (InterestRate, Balance) "
                   + "VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, a.getInterestRate());
            ps.setDouble(2, a.getBalance());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
        public boolean updateAccount(Account a) {
        String sql = "UPDATE Accounts SET "
                   + "  Balance = ? "
                   + "WHERE AccountID = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, a.getBalance());
            ps.setInt(2, a.getAccountID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
