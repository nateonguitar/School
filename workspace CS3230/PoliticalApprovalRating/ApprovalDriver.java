package brooksNathan;

import java.util.Scanner;

public class ApprovalDriver {

	public static void main(String[] args) {
		int samples 			= 0;
		int numPolls 			= 0;
		String input 			= "";
		double approvalsTotal 	= 0.0;
		double x 				= 0.0;
		boolean ok 				= false;
		Scanner sysIn 			= new Scanner(System.in);
		
		
		//----------------------------------------------------
		
		////////////
		// Part A //
		////////////
		cout("-- Part A --\n");
		//The main method calls verifyModel to prove that the getSample algorithm is OK.
		Approval.verifyModel();
		
		//Then it calls setPopulationMean to set the population mean approval at an 
		//unknown random value between 0.0 and 1.0. 
		Approval.setPopulationMean();
		
		//Then main asks the user for a desired number of samples, and it instantiates 
		//an Approval object, poll. 
		while(!ok){
			try{
				cout("Enter number of samples in poll: ");
				input = sysIn.next();
		
				// if input is a valid number
				samples = Integer.parseInt(input);
				ok = true;
			}
			catch(Exception e){
				System.out.println(" -- You must enter a whole number --");
			}
		}
		ok = false;
		Approval poll = new Approval();
		
		poll.doPoll(samples);
		
		cout("Poll's Average = " + Double.toString(((int) (poll.getMean() * 10000.0d)) / 100.0d) + "%\n");
		cout("standard deviation of poll's average = " + Double.toString(((int) (poll.getVariance() * 10000.0d)) / 100.0d) + "%\n");
		cout("\nPopulation average = " + Double.toString(((int) (Approval.getPopulationMean() * 10000.0d)) / 100.0d) + "%\n");
		
		
		
		//-----------------------------------------------------
		
		
		
		
		////////////
		// Part B //
		////////////
		cout("\n\n-- Part B --\n");
		
		Approval.setPopulationMean();
		cout("PopulationMean = " + Approval.getPopulationMean() + "\n");
		
		while(!ok){
			try{
				cout("\nEnter number of polls: ");
				input = sysIn.next();
				numPolls = Integer.parseInt(input);
				ok = true;
			}
			catch(Exception e){
				System.out.println(" -- You must enter a whole number --");
			}
		}
		
		ok = false;
		
		while(!ok){
			try{
				cout("\nEnter number of samples per poll: ");
				input = sysIn.next();
				samples = Integer.parseInt(input);
				ok = true;
			}
			catch(Exception e){
				System.out.println(" -- You must enter a whole number --");
			}
		}
		
		
		approvalsTotal = 0;
		for(int i=0; i<numPolls; i++){
			poll.doPoll(samples);
			approvalsTotal += poll.getMean();
		}
		
		// to make the math more readable
		x = approvalsTotal / (numPolls);
		
		
		
		
		// do this (<x2> - <x>2) / (samples – 1) 
		// and display the answer as "samplesMinusOne"?
		
		// this part confused me but I ran out of time.  I wasn't sure what the heck
		// "samplesMinusOne" was supposed to represent
		cout("samplesMinusOne: " + Double.toString(
			(((x * x)/(samples) - (x/samples)*(x/samples))) / (samples - 1)) 
		);
	} // end main
	
	//---------------------------------------
	
	
	// I just wanted to see if I liked this.......go C++!
	public static void cout(String string){
		System.out.print(string);
	}
}//end class