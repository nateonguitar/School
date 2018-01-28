import java.io.*;

public class P2PartD // TLogic
{
   public static void main(String args[])
   {
      System.out.println(ternaryFunction('F', 'F', 'F'));
      System.out.println(ternaryFunction('F', 'F', 'T'));
      System.out.println(ternaryFunction('F', 'F', 'U'));
      
      System.out.println(ternaryFunction('F', 'T', 'F'));
      System.out.println(ternaryFunction('F', 'T', 'T'));
      System.out.println(ternaryFunction('F', 'T', 'U'));
      
      System.out.println(ternaryFunction('F', 'U', 'F'));
      System.out.println(ternaryFunction('F', 'U', 'T'));
      System.out.println(ternaryFunction('F', 'U', 'U'));
      
      System.out.println(ternaryFunction('T', 'F', 'F'));
      System.out.println(ternaryFunction('T', 'F', 'T'));
      System.out.println(ternaryFunction('T', 'F', 'U'));
      
      System.out.println(ternaryFunction('T', 'T', 'F'));
      System.out.println(ternaryFunction('T', 'T', 'T'));
      System.out.println(ternaryFunction('T', 'T', 'U'));
      
      System.out.println(ternaryFunction('T', 'U', 'F'));
      System.out.println(ternaryFunction('T', 'U', 'T'));
      System.out.println(ternaryFunction('T', 'U', 'U'));      
      
      System.out.println(ternaryFunction('U', 'F', 'F'));
      System.out.println(ternaryFunction('U', 'F', 'T'));
      System.out.println(ternaryFunction('U', 'F', 'U'));
      
      System.out.println(ternaryFunction('U', 'T', 'F'));
      System.out.println(ternaryFunction('U', 'T', 'T'));
      System.out.println(ternaryFunction('U', 'T', 'U'));
      
      System.out.println(ternaryFunction('U', 'U', 'F'));
      System.out.println(ternaryFunction('U', 'U', 'T'));
      System.out.println(ternaryFunction('U', 'U', 'U'));      
   }

   private static char ternaryFunction(char p, char q, char r)
   {
      // Logical expression
      //f(p,q,r) = (~q AND r AND ~p) OR (~(r OR ~p))

      return TOR(TAND(TAND(TNOT(q), r), TNOT(p)), TNOT(TOR(r, TNOT(p))));
      
      
   }

   private static char TAND(char p, char q)
   {
      // Logical AND function
      // false as first parameter
      if(p == 'F' && q == 'F')
      {
         return 'F';
      }
      else if(p == 'F' && q == 'T')
      {
         return 'F';
      }
      else if(p == 'F' && p == 'U')
      {
         return 'F';
      }
      
      // true as first parameter
      else if(p == 'T' && p == 'F')
      {
         return 'F';
      }
      else if(p == 'T' && p == 'T')
      {
         return 'T';
      }
      else if(p == 'T' && p == 'U')
      {
         return 'U';
      }
      
      // unknown as first parameter
      else if(p == 'U' && p == 'F')
      {
         return 'F';
      }
      else if(p == 'U' && p == 'T')
      {
         return 'U';
      }
      else
      {
         return 'U';
      }
   }

   private static char TOR(char p, char q)
   {
      // Logical OR function  
      // false as first parameter
      if(p == 'F' || p == 'F')
      {
         return 'F';
      }
      else if(p == 'F' || p == 'T')
      {
         return 'T';
      }
      else if(p == 'F' || p == 'U')
      {
         return 'U';
      }
      
      // true as first parameter
      else if(p == 'T' || p == 'F')
      {
         return 'T';
      }
      else if(p == 'T' || p == 'T')
      {
         return 'T';
      }
      else if(p == 'T' || p == 'U')
      {
         return 'T';
      }
      
      // unknown as first parameter
      else if(p == 'U' || q == 'F')
      {
         return 'U';
      }
      else if(p == 'U' || p == 'T')
      {
         return 'T';
      }
      else
      {
         return 'U';
      }  
   }

   private static char TNOT(char p)
   {
      // Logical NOT function
      if(p == 'U')
      {
         return 'U';
      }
      else if(p == 'T')
      {
         return 'F';
      }
      else
      {
         return 'T';
      }
   }
} // end class