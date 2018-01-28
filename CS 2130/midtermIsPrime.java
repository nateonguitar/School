import java.io.*;

public class midtermIsPrime
{
   public static void main(String args[])
	{
      if(Long.parseLong(args[0]) > 1)
      {
   		long X;
   		if(args.length >= 1){
   			X = Long.parseLong(args[0]);       
   		}
         else
         {
            X = 1;
         }
         for (int i=2;i<1000000; i++)
         {
            if(isPrime(i))
            {
               System.out.println(i);

            }
         }
      }
      else
      {
         System.out.println("You entered an invalid number.  This requires a number greater than 1");
      }   
	return;
	}
   
   public static boolean isPrime(long X)
   {
      // 2 is the only even prime number we need to check for
       if(X == 2)           
        {
          return true;
        }
        // gets rid of all even numbers without looping
        if(X % 2 == 0)           
        {
            return false;
        }

      // skips every even number, we already checked all of them
       for(long i=3; i <= (X / 2); i+=2) 
       {
           if(X % i == 0)           
           {
               return false;
           }
       }
       return true; 
       // only reaches this code if it never returned false
   }
} // end class