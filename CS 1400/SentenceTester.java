import java.util.Scanner;

public class SentenceTester
{
    public static void main(String[] args)
    {
        Scanner stdIn = new Scanner(System.in);
        String sentence;    // user-entered sentence
        int lastCharPosition; // index of sentence's last character
        
        System.out.println("Enter a sentence: ");
        sentence = stdIn.nextLine();
        lastCharPosition = sentence.length() - 1;
        if (sentence.charAt(lastCharPosition) == '.' || sentence.charAt(lastCharPosition) == '?' || sentence.charAt(lastCharPosition) == '!')
        {
            System.out.println("Your sentence is valid.");
        }
        else
        {
            System.out.println("Invalid entry - your sentence needs a . ? or !");
        }
    } // end main
} // end class SentenceTester