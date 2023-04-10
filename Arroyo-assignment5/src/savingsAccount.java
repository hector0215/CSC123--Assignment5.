//Hector Arroyo harroyoruiz1@toromail.csudh.edu

public class savingsAccount extends Accounts{
	public savingsAccount(Person customer, String currency) {
		super("Savings",  customer, currency);
	}
	public int getAccountNumber() {
	    return accountNumber;
	}
	public double getDeposit() {
		return balance;
	}
	public double getWithdraw() {
		return balance;
	}
}
