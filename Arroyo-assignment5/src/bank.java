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
	private static File file = new File("C:\\Users\\Hector\\git\\CSC123\\Arroyo-assignment5\\src\\config.txt");

	public static boolean config() throws IOException, InterruptedException {
		boolean supportCurrencies;
		
		HashMap<String, String> config = readConfigFile(file);
		
		// if else statements to figure out config settings
		if("true".equals(config.get("support.currencies"))) {
			
			// if else settings to see if the source is going to be file or webservice
			if("file".equals(config.get("currencies.source"))) {
					 BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Hector\\Downloads\\exchange-rate.csv"));
			     	 String line;
			            while ((line = br.readLine()) != null) {
			                String[] parts = line.split(",");
			                String key = parts[0];
			                Double value = Double.parseDouble(parts[2]);
			                exchangeRates.put(key, value);
			            }
				}
			
			else if("webservice".equals(config.get("currencies.source"))) {
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
			}
			else{
				
			}
			supportCurrencies = true;
			return supportCurrencies;
		}
		
		// if support.currencies is set to false
		else {
			supportCurrencies = false;
			return supportCurrencies;
		}
		
		
	}
	private static Double parseDouble(String value) {
		// TODO Auto-generated method stub
		return null;
	}
	public static HashMap<String, String> readConfigFile(File file2) throws java.io.FileNotFoundException, IOException {
		HashMap<String, String> config = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file2))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    config.put(parts[0], parts[1]);
                }
            }
        }
        return config;
    }
	
	
	/*public static void rates() throws IOException, InterruptedException {
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
	}	*/
	public static Accounts openCheckingAccount() throws IOException, InterruptedException {
		System.out.println("Enter first name: ");
		String firstName = scnr.next();
		System.out.println("Enter last name: ");
		String lastName = scnr.next();
		System.out.println("Enter SSN: ");
		String SSN = scnr.next();
		System.out.println("Enter overdraft limit: ");
		double odl = scnr.nextDouble();
		if(config() == true){
			System.out.println("Enter Account Currency: ");
			String accountCurrency = scnr.next();
			Person customer = new Person(firstName, lastName, SSN);
			checkingAccount newAccount = new checkingAccount(customer, odl, accountCurrency);
			account.putIfAbsent(newAccount.getAccountNumber(),newAccount);
			System.out.println("Thank you, the account number is: " + newAccount.getAccountNumber());
			return newAccount;
		}
		else {
			Person customer = new Person(firstName, lastName, SSN);
			checkingAccount newAccount = new checkingAccount(customer, odl);
			account.putIfAbsent(newAccount.getAccountNumber(),newAccount);
			System.out.println("Thank you, the account number is: " + newAccount.getAccountNumber());
			return newAccount;
		}
		
	}
	public static Accounts openSavingsAccount() throws IOException, InterruptedException {
		System.out.println("Enter first name: ");
		String firstName = scnr.next();
		System.out.println("Enter last name: ");
		String lastName = scnr.next();
		System.out.println("Enter SSN: ");
		String SSN = scnr.next();
		if(config() == true){
			System.out.println("Enter Account Currency: ");
			String accountCurrency = scnr.next();
			Person customer = new Person(firstName, lastName, SSN);
			savingsAccount newAccount = new savingsAccount(customer, accountCurrency);
			account.putIfAbsent(newAccount.getAccountNumber(),newAccount);
			System.out.println("Thank you, the account number is: " + newAccount.getAccountNumber());
			return newAccount;
		}
		else {
			Person customer = new Person(firstName, lastName, SSN);
			savingsAccount newAccount = new savingsAccount(customer);
			account.putIfAbsent(newAccount.getAccountNumber(),newAccount);
			System.out.println("Thank you, the account number is: " + newAccount.getAccountNumber());
			return newAccount;
		}
	}
	public static void listAccounts() throws IOException, InterruptedException {
		//rates();
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
	public static void accountStatement() throws NoSuchAccountException, IOException, InterruptedException {
		//rates();
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
	public static void depositFunds() throws AccountClosedException, NoSuchAccountException, IOException, InterruptedException {
		//rates();
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
	public static void withdrawFunds() throws AccountClosedException, InsufficientBalanceException, NoSuchAccountException, IOException, InterruptedException {
		//rates();
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
	public static void closeAnAccount() throws NoSuchAccountException, IOException, InterruptedException {
		//rates();
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
	public static void currencyConversion()throws USDNotFound, IOException, FileNotFoundException, InterruptedException {
		String usersChosenCurrency;
		double amountUserIsSelling;
		String currencyUserIsBuying;

		System.out.println("The currency you are selling: ");
		usersChosenCurrency = scnr.next();
		double conversionRateOfChosenCurrency = exchangeRates.get(usersChosenCurrency);
		
		System.out.println("The amount you are selling : ");
		amountUserIsSelling = scnr.nextDouble();
		
		System.out.println("The currency you are buying : ");
		scnr.nextLine();
		currencyUserIsBuying = scnr.next();
		
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
	public static void accountInformation() throws IOException, InterruptedException {
		//rates();
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
            FileOutputStream fileOut = new FileOutputStream("bank_data.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
}

