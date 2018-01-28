/****************************************************
* Dean & Dean
*
*This program creates a GUI display of CRC cards
*****************************************************/

import java.util.Scanner;
import javax.swing.*; // for JFrame, JTextArea, and JSplitFrame

public class CRCCard
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      String input;
      
      System.out.print("Enter class name or 'q' to quit: ");
      input = stdIn.nextLine();
      
      while (!input.equalsIgnoreCase("q"))
      {
         JFrame frame = new JFrame("Class: " + input);
         JTextArea responsibilities = new JTextArea("RESPONSIBILITIES:\n");
         JTextArea collaborators = new JTextArea("COLLABORATORS:\n");
         JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, responsibilities, collaborators);
         
         frame.setSize(500,300);
         frame.add(splitPane);
         frame.setLocationByPlatform(true);
         frame.setVisible(true);
         frame.toFront();
         splitPane.setDividerLocation(0.67);
         
         System.out.print("Enter class name or 'q' to quit: ");
         input = stdIn.nextLine();
      } // end while
   } // end main
} // end class CRCCard