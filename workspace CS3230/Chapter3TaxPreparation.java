package brooksNathan;

/*************************************************
* Name: Chapter 3 - Tax Preparation.java
* Author: Nathan Brooks
* Due Date: 30 January 2016
* Assignment: Chapter 3 Assignment - Tax Preparation
* Description: calculates customers’ income taxes
*/

import java.text.DecimalFormat;
import java.util.Scanner;

public class Chapter3TaxPreparation {

	public static void main(String[] args) {
		// variables needed
		boolean done 			= false;
		boolean passCheck 		= false;
		char filingStatus 		= ' ';
		double grossIncome 		= 0;
		int numberOfExemptions 	= 0;
		double taxRate			= 0;
		double taxableIncome	= 0;
		double taxesOwed		= 0;
		Scanner in 				= new Scanner(System.in);
		String input 			= "";
		DecimalFormat formatter = new DecimalFormat("###,###,###.##");

		System.out.println("J O H N ' S   T A X   P R E P A R E R");

		do{
			//check for valid user input
			//ask again if not valid
			while(!passCheck){
				System.out.print("\nAre you (s)ingle, (m)arried, or (c)ohabiting?	Enter s, m, or c ==> ");
				input = in.next().toLowerCase();
				if(input.length() == 1 && (input.charAt(0) == 's' || input.charAt(0) == 'm' || input.charAt(0) == 'c')){
					filingStatus = input.charAt(0);
					passCheck = true;
				}
				else{
					System.out.println("-- Invalid entry\n-- Please choose s, m, or c");
				}
			}
			// set passCheck to false so we can reuse
			passCheck = false;
			
			
			while(!passCheck){
				System.out.print("\nGross income ==> $");
				input = in.next();
				try{
					grossIncome = Double.parseDouble(input);
					passCheck = true;
				}
				catch(Exception e){
					System.out.println("-- Please format your gross income as 999999999999999.99");
				}
			}
			// set passCheck to false so we can reuse
			passCheck = false;
			
			
			// set number of exemptions
			// check for valid user input
			// ask again if not valid
			while(!passCheck){
				System.out.print("\nNumber of exemptions ==> ");
				input = in.next();
				try{
					numberOfExemptions = Integer.parseInt(input);
					passCheck = true;
				}
				catch(Exception e){
					System.out.println("-- Please use only whole numbers as your number of exemptions");
				}
			}
			// set passCheck to false so we can reuse
			passCheck = false;
			
			// set taxable income
			taxableIncome = grossIncome - (numberOfExemptions * 1000);
			if(taxableIncome < 0){
				taxableIncome = 0;
			}
			
			// set tax rate
			switch(filingStatus){
			case 's':
				taxRate = 0.2;
				break;
			case 'm':
				taxRate = 0.25;
				break;
			case 'c':
				if(taxableIncome < 20000){
					taxRate = 0.1;
				}
				else if(taxableIncome >= 20000 && taxableIncome <= 50000){
					taxRate = 0.15;
				}
				else{
					taxRate = 0.3;
				}
				break;
			default:
				break;
			}
			
			
			//figure out taxes owed
			taxesOwed = taxableIncome * taxRate;
			
			
			//display results
			System.out.println("INCOME TAX SUMMARY");
			System.out.println("\ttax rate: " + taxRate*100 + "%");
			System.out.println("\ttaxable income: $" + formatter.format(taxableIncome));
			System.out.println("\ttaxes owed: $" + formatter.format(taxesOwed) + "\n");

			
			// process another customer
			System.out.print("Process another customer? (y/n): ");
			input = in.next().toLowerCase();
			if(input.length() == 1 && !(input.charAt(0) == 'y')){
				done = true;
			}
		}while(!done);
	}
}
