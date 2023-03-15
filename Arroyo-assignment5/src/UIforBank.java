//Hector Arroyo harroyoruiz1@toromail.csudh.edu
import java.io.IOException;
import java.util.Scanner;

public class UIforBank {
	private static Scanner scnr = new Scanner(System.in);
	private String type;
	public static void menu() {
		System.out.println("1 - Open a Checking account");
		System.out.println("2 - Open a Savings account");
		System.out.println("3 - List Account");
		System.out.println("4 - Account Statement");
		System.out.println("5 - Deposit funds");
		System.out.println("6 - Withdraw funds");
		System.out.println("7 - Close an account");
		System.out.println("8 - Save Transactions");
		System.out.println("9 - Exit");
		System.out.println("\nPlease enter your choice: ");
	}
	public static void main(String[] args) throws AccountClosedException, IOException {
		int customersChoice;
		boolean validChoice = false;

		menu();
		customersChoice = scnr.nextInt();
		while(customersChoice != 9) {
			if(customersChoice < 1 || customersChoice > 9) {
				System.out.println("Thats not a valid option! Please try again.");
		        menu();
		        customersChoice = scnr.nextInt();
			}
			try {
			    switch(customersChoice) {
			    case 1:
			        bank.openCheckingAccount();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 2:
			        bank.openSavingsAccount();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 3: 
			        bank.listAccounts();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 4:
			        bank.accountStatement();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 5: 
			        bank.depositFunds();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 6:
			        bank.withdrawFunds();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 7:
			        bank.closeAnAccount();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 8:
			    	bank.saveTransactions();
			        menu();
			        customersChoice = scnr.nextInt();
			    	break;
			    }
			} catch (AccountClosedException ACE) {
			    System.out.println("The transaction failed because the account is closed. The balance of the account is: $" + ACE.getBalance());
			    menu();
			    customersChoice = scnr.nextInt();
			}catch (InsufficientBalanceException IBE) {
			    System.out.println("There are not enough funds to be able to withdraw!!!");
			    menu();
			    customersChoice = scnr.nextInt();
			} catch(NoSuchAccountException NSAE) {
			    System.out.println("No such account exists!!");
				menu();
			    customersChoice = scnr.nextInt();
			}
		}
	}
}
