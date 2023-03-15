
public class AccountClosedException extends Exception {
	private double balance;

	public AccountClosedException(String message){
		super(message);
	}
    public AccountClosedException(String message, double balance) {
        super(message);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
	
}
