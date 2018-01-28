/****************************************************
* Dean & Dean
*
*This program demonstrates how to use the Calendar class
*****************************************************/

import java.util.*;

public class CalendarDemo
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      Calendar time = Calendar.getInstance();
      int day;
      int hour;
      
      System.out.println(time.getTime());    
      day = time.get(Calendar.DAY_OF_YEAR);  // parameters are int codes that specify
      hour = time.get(Calendar.HOUR_OF_DAY); // the kind of information desired
      
      System.out.println("day of year= " + day);
      System.out.println("your of day = " + hour);
      
      System.out.print("Enter number of days to add: ");
      day += stdIn.nextInt();
      System.out.print("Enter number of hours to add: ");
      hour += stdIn.nextInt();
      
      time.set(Calendar.DAY_OF_YEAR, day);
      time.set(Calendar.HOUR_OF_DAY, hour);
      System.out.println(time.getTime());
      
   } // end main
} // end class CalendarDemo