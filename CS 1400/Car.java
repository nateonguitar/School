/****************************************************
* Car.java
* Dean & Dean
*
*This class implements copy functionality fo ra car
*****************************************************/

public class Car
{
   private String make;
   private int year;
   private String color;
   
   //*********************************************
   
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
   
   //*********************************************
   
   public Car makeCopy()
   {
      Car car = new Car();
      
      car.make = this.make;
      car.year = this.year;
      car.color = this.color;
      return car;
   } // end makeCopy
   
   //*********************************************
   
   public void display()
   {
      System.out.printf("make= %s\nyear= %s\ncolor= %s\n", this.make, this.year, this.color);
   } // end display
} // end class Car