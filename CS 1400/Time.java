/****************************************************
* Dean & Dean
*
*This class stores time in the form of hours, minutes, and seconds.
*It prints the time using military format.
*****************************************************/

public class Time
{
   private int hours, minutes, seconds;
   
   //*******************************************************
   
   public Time(int h, int m, int s)
   {
      this.hours = h;
      this.minutes = m;
      this.seconds = s;
   }
   
   //*******************************************************
   
   public void printIt()
   {
      System.out.printf("%02d:%02d:%02d\n",
         this.hours, this.minutes, this.seconds);
   }
   
   //*******************************************************
   
   public static void main(String[] args)
   {
      Time time = new Time(3, 59, 0);
      time.printIt();
   } // end main
} // end class Time