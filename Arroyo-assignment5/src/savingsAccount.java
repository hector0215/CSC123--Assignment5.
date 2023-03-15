//Hector Arroyo harroyoruiz1@toromail.csudh.edu

public class savingsAccount extends Accounts{
	public savingsAccount(Person customer) {
		super("Savings",  customer);
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
