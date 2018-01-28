/****************************************************
* Car2.java
* Dean & Dean
*
*This class implements equals functionality for a car.
*****************************************************/

public class Car2
{
   private String make;
   private int year;
   private String color;
   
   //****************************************************
   
   public void setMake(String make)
   {
      this.make = make;
   }
   
   public void setYear(int year)
   {
      this.year = year;
   }
   
   public void setColor(String color)
   {
      this.color = color;
   }
   
   //**************************************
   
      // This method tests whether two cars hold the same data
   
   public boolean equals(Car2 otherCar)
   {
      return this.make.equalsIgnoreCase(otherCar.make) &&
             this.year == otherCar.year &&
             this.color.equals(otherCar.color);
   } // end equals   
} // end class Car2
