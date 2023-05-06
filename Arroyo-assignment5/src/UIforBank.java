//Hector Arroyo harroyoruiz1@toromail.csudh.edu
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class UIforBank {
	private static Scanner scnr = new Scanner(System.in);
	private String type;
	public static void menu() throws IOException, FileNotFoundException {

		System.out.println("1 -  Open a Checking account");
		System.out.println("2 -  Open a Savings account");
		System.out.println("3 -  List Account");
		System.out.println("4 -  Account Statement");
		System.out.println("5 -  Show Account Information");
		System.out.println("6 -  Deposit funds");
		System.out.println("7 -  Withdraw funds");
		System.out.println("8 -  Close an account");
		System.out.println("9 -  Save Transactions");
		System.out.println("10 - Currency Conversion");
		System.out.println("11 - Exit");
		System.out.println("\nPlease enter your choice: ");
	}
	public static void main(String[] args) throws AccountClosedException, IOException, FileNotFoundException, InterruptedException{
		bank.config();
		int customersChoice;
		menu();
		customersChoice = scnr.nextInt();
		while(customersChoice != 12) {
			if(customersChoice < 1 || customersChoice > 11) {
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
			    	bank.accountInformation();
			        menu();
			        customersChoice = scnr.nextInt();
			    	break;
			    case 6: 
			        bank.depositFunds();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 7:
			        bank.withdrawFunds();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 8:
			        bank.closeAnAccount();
			        menu();
			        customersChoice = scnr.nextInt();
			        break;
			    case 9:
			    	bank.saveTransactions();
			        menu();
			        customersChoice = scnr.nextInt();
			    	break;
			    case 10:
			    	bank.currencyConversion();
			        menu();
			        customersChoice = scnr.nextInt();
			    	break;
			    case 11:
			    	bank myBank = new bank();
			    	myBank.saveData();
			        System.exit(0);
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
			}catch (FileNotFoundException FNFE) {
			    System.out.println("** Currency file could not be loaded, Currency Conversion Service and Foreign Currency accounts are not available **");
			}catch(USDNotFound USDHNF) {
				System.out.println("One of the entered currencies must be USD!!!");
			}

			
		}
	}
}
