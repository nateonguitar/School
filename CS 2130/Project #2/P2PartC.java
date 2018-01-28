import java.io.*;

public class P2PartC
{
   public static void main(String args[])
	{
		long N1, N2;
		if(args.length >= 2){
			N1 = Long.parseLong(args[0]);
         N2 = Long.parseLong(args[1]);         
		}
      else
      {
         N1 = 1;
         N2 = 2;
      }
      
      // check all the numbers between the given values
      for (long i=0; i < N2 - N1; i++)
      {
         if(KNumber(i+N1) == true)
         {
            System.out.println(i + N1);
         }
      }

	return;
	}
   
   //------------------------------------
   
   private static boolean OddInt(long X)
   {
      if(X % 2 == 1)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   //------------------------------------
   
   private static boolean SquareInt(long X)
   {
      for(int i=1; i<(X/2); i += 2) // only check odds
      {
         if(i * i == X)
         {
            return true;
         }
         else if(i * i > X) // no point continuing to run this past X
         {
            return false;
         }
      }
      return false;
   }
   
   //------------------------------------
   
   private static boolean SymmetricInt(long X)
   {
      if(X == reverseDigits(X))
      {
         return true;
      }
      else
      {
         return false;
      }   
   }
   
   //------------------------------------
   
   private static boolean KNumber(long X)
   {
      if(OddInt(X))
      {
         if(SymmetricInt(X))
         {
            if(SquareInt(X))
            {
               return true;
            }
            else
            {
               return false;
            }
         }
         else
         {
            return false;
         }      
      }
      else
      {
         return false;
      }
   }
   
   //-----------------------------------
   // Yes I wrote this function myself
   //-----------------------------------
   
   public static long reverseDigits(long x)
	{
		long y = 0;
      int intLength = 0; // to test how many digits x has
      
		for(int i=1; i<=x; i*=10)
		{
         if(x >= i)
         {
            intLength++; // count how many digits x has
         }
		}
      
      for(int i=0; i<intLength; i++)
      {
         // shift result to the left one
         y *= 10;
         
         // add the remainder from dividing by 10
         y += x % 10;
         
         // shift x right one and truncates off the decimal because it is a long
         x /= 10;
      }
      
	return y;
	}
} // end class