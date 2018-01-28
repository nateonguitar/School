/*******************************************
* Dean and Dean
*
* This class implements a store that sells toy bears
*******************************************/
import java.util.Scanner;
import java.util.ArrayList;

public class BearStore
{
   ArrayList<Bear> bears = new ArrayList<>();
   
   //***********************************************************
   
      // fill store with specified number of standard teddy bears
   public void addStdBears(int num)
   {
      for (int i=0; i<num; i++)
      {
         bears.add(new Bear("Acme", "brown teddy"));
      }
   }  
   
   //***********************************************************
   
      // fill the store with specified number of customized bears.
   public void addUserSpecifiedBears(int num)
   {
      for (int i=0; i<num; i++)
      {
         bears.add(getUserSpecifiedBear()); // returned anonymous object becomes the argument in this method call
      }
   }   
   
   //**********************************************************
   
      // prompt user for bear's maker and type and return bear
   private Bear getUserSpecifiedBear()
   {
      Scanner stdIn = new Scanner(System.in);
      String maker, type;
      
      System.out.print("Enter bear's maker: ");
      maker = stdIn.nextLine();
      System.out.print("Enter bear's type: ");
      type = stdIn.nextLine();
      return new Bear(maker, type);
   } // end getUserSpecifiedBear() 
   
   //**********************************************************
   
      // print all the bears in the store
   public void displayInventory()
   {
      for (Bear bear : bears) // for each loop
      {
         bear.display();
      }
   }
   
   //**********************************************************
   
   public static void main(String[] args)
   {
      BearStore store = new BearStore();
      store.addStdBears(3);
      store.addUserSpecifiedBears(2);
      store.displayInventory();
   } // end main
} // end class BearStore

