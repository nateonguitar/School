// Program Proj2A.java
// Function: f is the truth value of a logical proposition.

import java.io.*;

public class P2PartA
{
   public static void main(String args[])
   {
      System.out.println(LProp('F', 'F', 'F'));
      System.out.println(LProp('F', 'F', 'T'));
      System.out.println(LProp('F', 'T', 'F'));
      System.out.println(LProp('F', 'T', 'T'));
      System.out.println(LProp('T', 'F', 'F'));
      System.out.println(LProp('T', 'F', 'T'));
      System.out.println(LProp('T', 'T', 'F'));
      System.out.println(LProp('T', 'T', 'T'));
   }

   private static char LProp(char p, char q, char r)
   {
      // Logical expression
      //f(p,q,r) = (~q AND r AND ~p) OR (~(r OR ~p))

      return ORlogic(ANDlogic(ANDlogic(NOTlogic(q), r), NOTlogic(p)), NOTlogic(ORlogic(r, NOTlogic(p))));
   }

   private static char ANDlogic(char p, char q)
   {
      // Logical AND function
      char f = 'F';
      if(p == 'T' && q == 'T')
      {
         f = 'T';
      }
      return f;
   }

   private static char ORlogic(char p, char q)
   {
      // Logical OR function  
      char f = 'F';
      if(p == 'T' || q == 'T')
      {
         f = 'T';
      }
      return f;   
   }

   private static char NOTlogic(char p)
   {
      // Logical NOT function
      if(p == 'T')
      {
         return 'F';
      }
      else
      {
         return 'T';
      }
   }
} // end class