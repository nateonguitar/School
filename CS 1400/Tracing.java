/*******************************
* Tracing.java
* Nathan Brooks
*
* This program does some simple math.
*******************************/

public class Tracing
   {
   public static void main(String[] args)
      {
      int a = 20;
      int b;
      double x = 3.5;
      String s = "All";
      char ch;
      
      x += a;
      x--;
      a /= 4 - 1;
      b = s.length();
      b += 4;
      s += "is well";
      ch = s.charAt(b);
      System.out.println("a = " + a + ", b = " + b);
      System.out.println("x = " + x + "\ns = " + s);
      System.out.println("ch = " + ch);
      } // End main
   } // End class Tracing