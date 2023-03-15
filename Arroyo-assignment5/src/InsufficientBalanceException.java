//Hector Arroyo harroyoruiz1@toromail.csudh.edu
public class InsufficientBalanceException extends Exception{
	
    private double balance;

	public InsufficientBalanceException(String message, double balance) {
        super(message);
        this.balance = balance;
    }

	public InsufficientBalanceException() {
		
	}
	
	public InsufficientBalanceException(String message) {
        super(message);
    }
}
