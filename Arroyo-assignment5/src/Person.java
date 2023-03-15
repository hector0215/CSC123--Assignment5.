//Hector Arroyo harroyoruiz1@toromail.csudh.edu

import java.util.*;

public class Person {
	private String firstName;
	private String lastName;
	private String SSN;
	private Scanner scnr = new Scanner(System.in);


	public Person(String fName, String lName, String SSN) {
		setFirstName(fName);
		setLastName(lName);
		setSSN(SSN);
	}
	
	public Person() {}
	
	public void setFirstName(String S) {
		firstName = S;
	}
	public void setLastName(String S) {
		lastName = S;
	}
	
	public void setSSN(String S) {
		SSN = S;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getSSN() {
		return SSN;
	}
	
	@Override
	public String toString() {
		return firstName + " : "+ lastName + " : " + SSN;
	}
	
}
