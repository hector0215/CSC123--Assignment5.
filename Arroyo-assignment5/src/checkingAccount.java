//Hector Arroyo harroyoruiz1@toromail.csudh.edu

public class checkingAccount extends Accounts{
	protected double overDraftLimit;
	
	public checkingAccount(Person customer, double overDraft) {
		super("Checking",  customer);
		this.overDraftLimit = overDraft;
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
