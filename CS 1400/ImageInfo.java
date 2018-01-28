/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* This program lets you enter a phone number in
* and splits its pieces up into segments.
*
* It also makes sure you don't type any words.
*******************************************/
import java.util.Scanner;
import javax.swing.ImageIcon;

public class ImageInfo
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      ImageIcon icon;
      
      System.out.print ("enter image filename \n(try lotro-the-shire.jpg): ");
      icon = new ImageIcon(stdIn.nextLine());
      System.out.println("image width = " + icon.getIconWidth());
      System.out.println("image height = " + icon.getIconHeight());
   } // end of main
} // end of class ImageInfo