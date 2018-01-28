import java.io.*;

public class P2PartB4
{
   public static void main(String args[])
   {
      System.out.println(LCircuit(0, 0, 0, 0));
      System.out.println(LCircuit(0, 0, 0, 1));
      System.out.println(LCircuit(0, 0, 1, 0));
      System.out.println(LCircuit(0, 0, 1, 1));
      System.out.println(LCircuit(0, 1, 0, 0));
      System.out.println(LCircuit(0, 1, 0, 1));
      System.out.println(LCircuit(0, 1, 1, 0));
      System.out.println(LCircuit(0, 1, 1, 1));
      System.out.println(LCircuit(1, 0, 0, 0));
      System.out.println(LCircuit(1, 0, 0, 1));
      System.out.println(LCircuit(1, 0, 1, 0));
      System.out.println(LCircuit(1, 0, 1, 1));
      System.out.println(LCircuit(1, 1, 0, 0));
      System.out.println(LCircuit(1, 1, 0, 1));
      System.out.println(LCircuit(1, 1, 1, 0));
      System.out.println(LCircuit(1, 1, 1, 1));
   }

   private static int LCircuit(int x, int y, int z, int w)
   {
      // Logical expression
      //f(p,q,r) = (~q AND r AND ~p) OR (~(r OR ~p))
      //f(x, y, z, w) = y’(z + x) + z’(wx + x’y)

      return ORgate( ANDgate(NOTgate(y), ORgate(z, x)), ANDgate(NOTgate(z), ORgate(ANDgate(w, x), ANDgate(NOTgate(x), y))));
     // return ORgate(ANDgate(ANDgate(NOTgate(q), r), NOTgate(p)), NOTgate(ORgate(r, NOTgate(p))));
      
   }

   private static int ANDgate(int p, int q)
   {
      // Logical AND function
      int f = 0;
      if(p == 1 && q == 1)
      {
         f = 1;
      }
      return f;
   }

   private static int ORgate(int p, int q)
   {
      // Logical OR function  
      char f = 0;
      if(p == 1 || q == 1)
      {
         f = 1;
      }
      return f;   
   }

   private static int NOTgate(int p)
   {
      // Logical NOT function
      if(p == 1)
      {
         return 0;
      }
      else
      {
         return 1;
      }
   }
} // end class