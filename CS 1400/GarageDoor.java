import java.util.Scanner;

public class GarageDoor
{
    public static void main(String[] args)
    {
        Scanner stdIn = new Scanner(System.in);
        String entry;               // user's entry - enter key or q
        boolean upDirection = true; // Is the current direction up?
        boolean inMotion = false;   // Is garage door currently moving?
        
        System.out.println("GARAGE DOOR OPENER SIMULATOR\n");
        
        do
        {
            System.out.print("Press Enter, or enter 'q' to quit: ");
            entry = stdIn.nextLine();
            
            while (!entry.equals("") && !entry.equalsIgnoreCase("q"))
            {
               System.out.println("Invalid entry.");
               System.out.print("Press Enter, or enter 'q': ");
               entry = stdIn.nextLine();
            }
            
            if (entry.equals(""))      // pressing Enter generates ""
            {
               inMotion = !inMotion;      // button toggles run state
               if (inMotion)
               {
                  if (upDirection)
                  {
                     System.out.println("Moving up");
                  }
                  else
                  {
                     System.out.println("Moving down");
                  }
               }
               else
               {
               System.out.println("Stopped");
               upDirection = !upDirection;   // direction reverses at stop
               }
            } // end of if entry ""
        } while (entry.equals(""));
    } // end main
} // end class GarageDoor