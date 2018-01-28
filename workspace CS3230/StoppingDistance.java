/*************************************************
* Name: StoppingDistance.java
* Author: Nathan Brooks
* Due Date: 20 January 2016
* Assignment: Week 1 In Class Assignment - Stopping Distance
* Description: Checks if a tailgate distances are far enough to
*              to prevent a crash at given speeds.
*/

import java.util.Scanner;

public class StoppingDistance {
    public static void main(String[] args) {
        
        double speed = 0;
        double tailgateDistance = 0;
        double stoppingDistance = 0;
        Scanner stdIn = new Scanner(System.in);
        boolean done = false;
        
        while(done == false){
            try{
                // capture speed
                System.out.print("Enter your speed (in mph): ");
                speed = Double.parseDouble(stdIn.next());

                // capture tailgait distance
                System.out.print("Enter your tailgate ditance(in feet): ");
                tailgateDistance = Double.parseDouble(stdIn.next());

                // figure out stopping distance
                stoppingDistance = speed * (2.25 + speed / 21);

                // display appropriate message
                if(stoppingDistance < tailgateDistance){
                    System.out.println("No problem.");
                }
                else if(stoppingDistance == tailgateDistance){
                    System.out.println("Minor wreck.");
                }
                else{
                    System.out.println("Major wreck!");
                }
            }
            catch (Exception e){
                System.out.println("Input myst be a number");
            }
            
            // try again?
            System.out.println("\n\nTry another(y/n)? ");
            if(!(stdIn.next().equals("y"))){
                done = true;
            }
        } // end while (done == false)
    }// end main
} // end class StoppingDistance
