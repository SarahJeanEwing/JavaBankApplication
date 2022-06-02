/** S. Ewing, 2022, All rights reserved. 
  */ 

public class Account {
    
    private int accountID;
    private double interestRate;
    private double balance;

    Account(int accountID, Double interestRate, Double balance) {
        this.accountID = accountID;
        this.interestRate = interestRate;
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
