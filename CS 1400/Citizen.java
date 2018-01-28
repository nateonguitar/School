/*******************************************
* Dean and Dean
*
* This represents an element in a network of citizens
*******************************************/

import java.util.*;

public class Citizen
{
   private static int nextID = 0;      // for unique IDs
   public final int ID = nextID++;     // cannot change!
   private Set<Integer> friends = new TreeSet<>();
   private int delay;
   
   //************************************************************
   
   public void addFriend(int friendID)
   {
      this.friends.add(friendID);
   }
   
   public Set<Integer> getFriends()
   {
      return this.friends;
   }
   
   public void setDelay(int delay)
   {
      this.delay = delay;
   }
   
   public int getDelay()
   {
      return this.delay;
   }
   
   //*******************************************************
   
   public String toString()
   {
      return String.format("%d\t%s", ID, friends);
   }
} // end class Citizen