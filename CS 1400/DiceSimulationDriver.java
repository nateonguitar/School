/*******************************************
* Nathan Brooks
*
* CS 1400 
*This is the driver for the DiceSimulation class
*******************************************/
import java.util.Scanner;

public class DiceSimulationDriver
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      String choice;          // user's choice of action
      boolean done = false;   // user's quit flag
   
      DiceSimulation diceSimulation = new DiceSimulation();
      System.out.println("Welcome to the dice throwing simulator!\n");
   
      do
      {
         System.out.println(
            "Options: (n)ew simulation, (a)dditional rolls, (p)rint, (q)uit");
         System.out.print("Enter n, a, p, or q ==> ");
         choice = stdIn.nextLine();
      
         switch (choice.charAt(0))
         {
            case 'n': case 'N':
               diceSimulation.newSimulation();
               break;
            case 'a': case 'A':
               diceSimulation.additionalRolls();
               break;
            case 'p': case 'P':
               diceSimulation.printReport();
               break;
            case 'q': case 'Q':
               done = true;
               break;                            
            default:
               System.out.println("Invalid selection.");
         } // end switch
      } while (!done);
   } // end main
} // end