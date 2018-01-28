/*****************************************
 * Nathan Brooks
 * CS 1400
 * 
 * This program takes your height and weight
 * and tells you your body mass index
 *****************************************/

import java.util.Scanner;

public class BodyMassIndex 
{
	public static void main(String[] args) 
	{
		Scanner stdIn = new Scanner(System.in);
		
		double inches = 0;
		double pounds = 0;
		boolean valid;
		double bmi;
		
		System.out.print("Enter height in inches: ");
		
		do
		{
			valid = true;
			try
			{
				inches = Double.parseDouble(stdIn.next());
				if (inches <=0)
				{
					throw new IllegalArgumentException("Invalid inches value. must be positive.");
				}
			}
			
			catch (NumberFormatException e)
			{
				valid = false;
						System.out.println("invalid inches value. must be a decimal number.");
			}
			
			catch (IllegalArgumentException e)
			{
				valid = false;
				System.out.println(e.getMessage());
			}
			
			if (!valid)
			{
				System.out.print("re-enter height inches: ");
			}
		} while (!valid); // end do
		
		System.out.print("Enter weight in pounds: ");
		
		do
		{
			valid = true;
			try
			{
				pounds = Double.parseDouble(stdIn.next());
				if (pounds <=0)
				{
					throw new IllegalArgumentException("Invalid pounds value. must be positive.");
				}
			}
			
			catch (NumberFormatException e)
			{
				valid = false;
						System.out.println("invalid pounds value. must be a decimal number.");
			}
			
			catch (IllegalArgumentException e)
			{
				valid = false;
				System.out.println(e.getMessage());
			}
			
			if (!valid)
			{
				System.out.print("re-enter weight in pounds: ");
			}
		} while (!valid); // end do
		
		bmi = 704.0 * pounds / (inches * inches);
		System.out.println("\nheight = " + inches + " inches");
		System.out.println("weight = " + pounds + " pounds");
		System.out.printf("body mass index = %.1f\n", bmi);
	} // end main
} // end class BodyMassIndex
