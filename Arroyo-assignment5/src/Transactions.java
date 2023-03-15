//Hector Arroyo harroyoruiz1@toromail.csudh.edu

public class Transactions {
    protected int transNumber;
    protected String transactionType;
    protected int accountNumber;
    protected double transactionAmount;
    protected static int unissuedTransactionNumbers = 1;

    public Transactions(int accountNumber, String type, double amount) {
        this.transactionAmount = amount;
        transNumber = unissuedTransactionNumbers++;
    	this.accountNumber = accountNumber;
        if (type.equals("credit")) {
            this.transactionType = "credit";
        } else if (type.equals("debit")) {
            this.transactionType = "debit";
        }
    }

    public int getTransactionNumber() {
        return transNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public String toString() {
        return transNumber + " : " + transactionType + " : $" +  transactionAmount + "\n";
    }
}

