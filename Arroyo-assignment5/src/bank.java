import java.util.*;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class bank {
	private static Scanner scnr = new Scanner(System.in);
	public bank() {}
	private static HashMap<Integer, Accounts> account = new HashMap<>();
	private static ArrayList<Transactions> transaction = new ArrayList<Transactions>();
	public static HashMap<String, Double> exchangeRates = new HashMap<>();
	private String firstName;
	private String lastName;
	private String SSN;
	private static int customersChoice;
	private boolean validChoice = false;
	
	public static void currencyConversion()throws USDNotFound, IOException, FileNotFoundException, InterruptedException {
		String usersChosenCurrency;
		double amountUserIsSelling;
		String currencyUserIsBuying;
		
		HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"))
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Scanner scanner = new Scanner(httpResponse.body());
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String currency = parts[0];
            Double rate = Double.parseDouble(parts[2]);
            exchangeRates.put(currency, rate);
            
        }
		
		System.out.println("The currency you are selling: ");
		usersChosenCurrency = scnr.nextLine();
		double conversionRateOfChosenCurrency = exchangeRates.get(usersChosenCurrency);
		
		System.out.println("The amount you are selling : ");
		amountUserIsSelling = scnr.nextDouble();
		
		System.out.println("The currency you are buying : ");
		scnr.nextLine();
		currencyUserIsBuying = scnr.nextLine();
		
		if(!usersChosenCurrency.equals("USD") && !currencyUserIsBuying.equals("USD")) {
			throw new USDNotFound("One of the entered currencies must be USD!!!");
		}
		if(usersChosenCurrency.equals("USD")) {
			double amountAfterConversion = amountUserIsSelling/conversionRateOfChosenCurrency;
			System.out.printf("The exchange rate is %f and you will get %s $%.2f \n",conversionRateOfChosenCurrency,currencyUserIsBuying,amountAfterConversion);
		}
		if(currencyUserIsBuying.equals("USD")) {
			double amountAfterConversion = amountUserIsSelling * conversionRateOfChosenCurrency;
			System.out.printf("The exchange rate is %f and you will get %s $%.2f \n",conversionRateOfChosenCurrency,currencyUserIsBuying,amountAfterConversion);
		}
	}
	public static Accounts openCheckingAccount() {
		System.out.println("Enter first name: ");
		String firstName = scnr.next();
		System.out.println("Enter last name: ");
		String lastName = scnr.next();
		System.out.println("Enter SSN: ");
		String SSN = scnr.next();
		System.out.println("Enter overdraft limit: ");
		double odl = scnr.nextDouble();
		System.out.println("Enter Account Currency: ");
		String accountCurrency = scnr.next();
		System.out.println(exchangeRates.get(accountCurrency));
		
		Person customer = new Person(firstName, lastName, SSN);
		checkingAccount newAccount = new checkingAccount(customer, odl, accountCurrency);
		account.putIfAbsent(newAccount.getAccountNumber(),newAccount);
		System.out.println("Thank you, the account number is: " + newAccount.getAccountNumber());
		return newAccount;
	}
	public static Accounts openSavingsAccount() {
		String accountCurrency;
		System.out.println("Enter first name: ");
		String firstName = scnr.next();
		System.out.println("Enter last name: ");
		String lastName = scnr.next();
		System.out.println("Enter SSN: ");
		String SSN = scnr.next();
		System.out.println("Enter Account Currency: ");
		accountCurrency = scnr.next();
		
		Person customer = new Person(firstName, lastName, SSN);
		savingsAccount newAccount = new savingsAccount(customer, accountCurrency);
		account.putIfAbsent(newAccount.getAccountNumber(),newAccount);
		System.out.println("Thank you, the account number is: " + newAccount.getAccountNumber());
		return newAccount;
	}
	public static void listAccounts() {
	    if (account.isEmpty()) {
	        System.out.println("No accounts found.");
	    } else {
	        for (Integer i : account.keySet()) {
	        	Accounts acc = account.get(i);
	            System.out.println(acc);
	        }
	    }
	    System.out.println();
	}
	public static void accountStatement() throws NoSuchAccountException {
	    boolean accountFound4 = false;
	    System.out.println("Enter account number: ");
	    int customersAccountNumber3 = scnr.nextInt();
	    
	    for(Integer i : account.keySet()) {
	        if(account.containsKey(customersAccountNumber3)) {
	        	Accounts acc = account.get(customersAccountNumber3);
	            accountFound4 = true;
	            if (acc.getTransactions().isEmpty()) {
	                System.out.println("No transactions found.");
	            } else {
	                for (Transactions trns : acc.getTransactions()) {
	                    System.out.println(trns);
	                }
	            }
	            break;
	        }
	    }
	    
	    if(accountFound4 == false) {
	    	throw new NoSuchAccountException();
	    }
	    System.out.println();
	}
	public static void depositFunds() throws AccountClosedException, NoSuchAccountException {
		boolean accountFound = false;
		double newBalance;
		System.out.println("Enter account number: ");
		int customersAccountNumber = scnr.nextInt();

		if(account.isEmpty()) {
		    System.out.println("No accounts have been created.");
		    System.out.println();
		    customersChoice = scnr.nextInt();
		} else {
		    if(account.containsKey(customersAccountNumber)) {
		        Accounts acc = account.get(customersAccountNumber);
		        if(acc.isOpen() == false){
		            throw new AccountClosedException("Deposit failed because the account is closed, the balance is: $" , acc.getUserCurrencyBalance());
		        } else {
		            System.out.println("Enter the amount to deposit: ");
		            double depositAmount = scnr.nextDouble();
		            newBalance = acc.deposit(depositAmount);
		            acc.usersSelectedCurrency = newBalance;
		            System.out.println("Deposit successful, the new balance is: $" + acc.getUserCurrencyBalance());
		            Transactions creditTransaction = new Transactions(acc.accountNumber, "credit",depositAmount);
		            transaction.add(creditTransaction);
		            accountFound = true;
		        }
		    }
		}

		if(accountFound == false) {
		    throw new NoSuchAccountException();
		}
	}
	public static void withdrawFunds() throws AccountClosedException, InsufficientBalanceException, NoSuchAccountException {
		boolean accountFound2 = false;
		double newBalances2;
		System.out.println("Enter account number: ");
		int chosenCustomersAccountNumber = scnr.nextInt();

		if(account.isEmpty()) {
		    System.out.println("No accounts have been created.");
		    System.out.println();
		    chosenCustomersAccountNumber = scnr.nextInt();
		} else {
		    if(account.containsKey(chosenCustomersAccountNumber)) {
		        Accounts acc = account.get(chosenCustomersAccountNumber);
		        if(acc.isOpen() == false){
		            throw new AccountClosedException("Withdrawal failed because the account is closed, the balance is: $", acc.getUSDBalance());
		        } else {
		            System.out.println("Enter the amount to withdraw: ");
		            double withdrawAmount = scnr.nextDouble();
		            newBalances2 = acc.withdraw(withdrawAmount);
		            acc.balance = newBalances2;
		            if(acc.withdrawSucces() == true) {
		                System.out.println("Withdrawal successful, the new balance is: $" + acc.getUSDBalance());
		                Transactions debitTransaction = new Transactions(acc.accountNumber, "debit", withdrawAmount);
		                transaction.add(debitTransaction);
		            } else {
		                throw new InsufficientBalanceException("Withdrawal failed due to insufficient funds, the balance is: $", acc.getUSDBalance());
		            }
		            accountFound2 = true;
		        }
		    }
		}

		if(accountFound2 == false) {
		    throw new NoSuchAccountException();
		}
	}
	public static void closeAnAccount() throws NoSuchAccountException {
		boolean accountFound3 = false;
		double accountBalance = 0;
		System.out.println("Enter account number: ");
		int customersAccountNumber2 = scnr.nextInt();
		
    	for(Integer i : account.keySet()) {
        	if(account.containsKey(customersAccountNumber2)) {
        		Accounts acc = account.get(customersAccountNumber2);
        		accountFound3 = true;
        		accountBalance = acc.getUSDBalance();
        		acc.closeAccount();
        	}
    	}
    	if(accountFound3 == false) {
    		throw new NoSuchAccountException();
    	}else {
    		System.out.println("Account closed, current balance is $" + accountBalance + ", deposits are no longer possible.");
    	}
	}
	public static void saveTransactions() throws IOException, NoSuchAccountException {
		File file = new File("transactions.txt");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
	    boolean accountFound4 = false;
	    System.out.println("Enter account number: ");
	    int customersAccountNumber3 = scnr.nextInt();
	    for(Integer i : account.keySet()) {
	        if(account.containsKey(customersAccountNumber3)) {
	        	Accounts acc = account.get(customersAccountNumber3);
	            accountFound4 = true;
	            if (acc.getTransactions().isEmpty()) {
	                System.out.println("No transactions found.");
	            } else {
	                for (Transactions trns : acc.getTransactions()) {
	                	pw.print(trns);
	                	
	                }
	            }
	            break;
	        }
	    }
	    if(accountFound4 == false) {
	    	throw new NoSuchAccountException();
	    }
	    System.out.println();
	    pw.close();
	}	
	
	public static void accountInformation() {
		System.out.println("Enter account number: ");
		int customersAccountNumber = scnr.nextInt();
		
		if(account.isEmpty()) {
		    System.out.println("No accounts have been created.");
		    System.out.println();
		    customersChoice = scnr.nextInt();
		} else {
		    if(account.containsKey(customersAccountNumber)) {
		        Accounts acc = account.get(customersAccountNumber);
		        System.out.println("Account Number: " + customersAccountNumber);
		        System.out.println("Name: " + acc.customer.getFirstName() + " " + acc.customer.getLastName());
		        System.out.println("SSN: " + acc.customer.getSSN());
		        System.out.println("Currency: " + acc.currency);
		        System.out.println("Currency Balance: " + acc.getUserCurrencyBalance());
		        System.out.println("USD Balance: " + acc.getUSDBalance());
		        System.out.println();
		    }
		}
	}
    public void saveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("bank_data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Bank data saved to bank_data.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
}

