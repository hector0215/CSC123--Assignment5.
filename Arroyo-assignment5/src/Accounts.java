//Hector Arroyo harroyoruiz1@toromail.csudh.edu

import java.util.*;

public abstract class Accounts {
	protected String fName;
	protected String lName;
	protected String SSN;
	protected static int unissuedAccountNumbers = 0;
	protected int accountNumber;
	protected Person customer;
	protected String accountType;
	protected boolean accountOpen;
	protected double balance;
	protected boolean withdrawSuccess = true;
	protected String status;
	protected String currency;
	protected double usersSelectedCurrency;
    private ArrayList<Transactions> transactions;
    

	public Accounts(String accountType, Person customer, String currency) {
		unissuedAccountNumbers++;
		accountNumber = unissuedAccountNumbers;
		this.accountType = accountType;
		accountOpen=true;
		this.customer = customer;
		this.currency = currency;
	    this.transactions = new ArrayList<Transactions>();
	}
	
	public double deposit(double depositAmount) {
		if(depositAmount<0 || !isOpen());
		balance=balance+depositAmount;
	    Transactions transaction = new Transactions(accountNumber, "credit", depositAmount);
	    transactions.add(transaction);
		return balance;
	}
	
	public double withdraw(double withdrawAmount) throws InsufficientBalanceException{
	    if (this instanceof checkingAccount) {
	        if (balance - withdrawAmount + ((checkingAccount) this).overDraftLimit >= 0) {
	            balance = balance - withdrawAmount;
	            withdrawSuccess = true;
	        } else {
	            withdrawSuccess = false;
	            throw new InsufficientBalanceException();
	        }
	    } else {
	        if (balance - withdrawAmount >= 0) {
	            balance = balance - withdrawAmount;
	            withdrawSuccess = true;
	        } else {
	            withdrawSuccess = false;
	            throw new InsufficientBalanceException();
	        }
	    }
	    Transactions transaction = new Transactions(accountNumber, "debit", withdrawAmount);
	    transactions.add(transaction);
	    return balance;
	}
	
	public boolean withdrawSucces() {
		return withdrawSuccess;
	}
	
	public boolean isOpen() {
		return accountOpen;
	}
	
	public void closeAccount() {
		this.accountOpen=false;
	}
	
	public double getUSDBalance() {
		return balance * bank.currencyConversion.get(currency);
	}
	
	public double getUserCurrencyBalance() {
		usersSelectedCurrency = balance; 
		return usersSelectedCurrency;
	}

	public ArrayList<Transactions> getTransactions() {
		return transactions;
	}
	
	@Override
	public String toString() {
		status = isOpen() ? "Account Open" : "Account Closed";
		return accountNumber + " (" + this.accountType + ") " + ": " + customer + " : " + currency + " : " + getUserCurrencyBalance() + " : " + "USD" + " : $" + getUSDBalance() + " : " + status;
	}
}
