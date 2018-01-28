import java.util.Scanner;

public class NestedLoopRectangle
{
    public static void main(String[] args)
    {
        Scanner stdIn = new Scanner(System.in);
        int height, width;    // Rectangle's dimensions
        char printCharacter;
        
        System.out.print("Enter Height: ");
        height = stdIn.nextInt();
        System.out.print("Enter Width: ");
        width = stdIn.nextInt();
        System.out.print("Enter Character: ");
        printCharacter = stdIn.next().charAt(0);
        
        for (int row=1; row<=height; row++)
        {
            for (int col=1; col<=width; col++)
            {
               System.out.print(printCharacter);
            }
            System.out.println();   
        }
    } // end main
} // end class NestedLoopRectangle