/*******************************************************************
* Dean and Dean
* 
* this generates citizens, established friend relationships, and 
* propagates a message through the network of friends.
*******************************************************************/

import java.util.*; // Scanner, Map, and Set

public class CommunityDriver
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      Community community;
      Map<Integer, Citizen> citizens;
      Set<Integer> informedCitizens;
      
         // create the network
      System.out.print("Enter citizen & relationship quantities: ");
      community = new Community(stdIn.nextInt(), stdIn.nextInt());
      citizens = community.getCitizens();
      
      System.out.println("Citizen\nFriends");
      for (Integer id : citizens.keySet())
      {
            // use Citezen's toString method to display citizen info:
         System.out.println(citizens.get(id));   
      } // end for each   
      
         // propagate a message through it
      System.out.print("Enter information source ID: ");
      informedCitizens = community.spreadWord(stdIn.nextInt());
      System.out.println("Citizen\t Delay");
      for (Integer citizenID : informedCitizens)
      {
         System.out.printf("%d\t%d\n",
            citizenID, citizens.get(citizenID).getDelay());
      } // end for each   
   } // end main
} // end class CommunityDriver