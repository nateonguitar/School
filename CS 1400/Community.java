/*******************************************************************
* Dean and Dean
* 
* this describes community structure and behavior
*******************************************************************/

   // Random, Map, HashMap, Set, LinkedHashSet, Queue, ArrayDeque
import java.util.*;

public class Community
{
   private Map<Integer, Citizen> citizens = new HashMap<>();
   
   //******************************************************************
   
      // Postconditions: all connections are bidirectional
   public Community(int citizenQuantity, int relationQuantity)
   {
      Random random = new Random(0);
      Citizen citizen;                 // any citizen object
      int self, other;                 // ID numbers
      
      for (int i=0; i<citizenQuantity; i++)
      {
         citizen = new Citizen();
         citizens.put(citizen.ID, citizen);  // ID is public
      }
      for (int j=0; j<relationQuantity; j++)
      {
         self = random.nextInt(citizens.size());
         do
         {
            other = random.nextInt(citizens.size());
         } while (other == self || citizens.get(self).getFriends().contains(other));
         citizens.get(self).addFriend(other);
         citizens.get(other).addFriend(self);
      }
   } // end constructor
   
   //******************************************************************
   
   public Map<Integer,Citizen> getCitizens()
   {
      return this.citizens;
   } //end getCitizens()
   
   //******************************************************************
   
      // precondition: sender is part of an established network
      // Postcondition: return set includes all connected citizens
   public Set<Integer> spreadWord(int sender)
   {
      Set<Integer> informedCitizens = new LinkedHashSet<>();
      Queue<Integer> sendersQueue = new ArrayDeque<>();
      
      citizens.get(sender).setDelay(0);      // for originator
      informedCitizens.add(sender);
      sendersQueue.add(sender);
      do
      {
         sender = sendersQueue.remove();
         for (Integer friend : citizens.get(sender).getFriends())
         {
            if (!informedCitizens.contains(friend))
            {
               citizens.get(friend).setDelay(citizens.get(sender).getDelay() + 1);
               informedCitizens.add(friend);
               sendersQueue.add(friend);
            }
         } // end foreach uninformed friend
      } while (!sendersQueue.isEmpty());
      return informedCitizens;
   } // end spreadWord()   
} // end class Community