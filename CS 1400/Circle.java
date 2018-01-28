/****************************************************
* Nathan Brooks
*
* CS 1400
*
* This program lets you enter a radius and it will return
* values for diameter, circumference, and area
*****************************************************/
import javax.swing.JOptionPane;
import java.util.Scanner;

public class Circle
{
   private double radius;
   private double circumference;
   private double area;
   private double diameter;
   public String circle;
   
   //*************************************
      
   public void initialize()
   {
      Scanner stdIn = new Scanner(System.in);
      
      this.circle = circle;
      
      System.out.print("Please enter a radius: ");
      this.radius = stdIn.nextDouble();
   }
   
   //**************************************
   
   public void setRadius(double radius)
   {
      this.radius = radius;
   }
   
   //**************************************
   
   public void printCircleData()
   {
      diameter = this.radius * 2;
      circumference = Math.PI * diameter;
      area = Math.PI * (this.radius * this.radius);
      
      JOptionPane.showMessageDialog(null, "Radius: " + this.radius + 
      "\nDiameter: " + diameter + 
      "\nCircumference: " + circumference + 
      "\narea: " + area);
   }
   
   //**************************************
   
} // end class CircleDriver