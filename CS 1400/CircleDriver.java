/****************************************************
* CircleDriver.java
* Dean & Dean
*
* This is the driver for the Circle class.
*****************************************************/

public class CircleDriver
{
   public static void main(String[] args)
   {
      Circle circle;
      circle = new Circle();
   
      circle.initialize();
      circle.printCircleData();
      circle.setRadius(5);
      circle.printCircleData();
   } // end main
} // end class CircleDriver