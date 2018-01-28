/*******************************
* FriendlyHello.java
* Nathan Brooks
*
* This program displays a personallized Hello greeting.
*******************************/

import java.util.Scanner;

public class FriendlyHello
   {
   public static void main(String[] args)
      {
      Scanner stdIn = new Scanner(System.in);
      String name;
      System.out.print("Enter your name: ");
      name = stdIn.nextLine();
      System.out.println(name + ", You're a bitch!!!");
      } // End main
   } // End class FriendlyHello