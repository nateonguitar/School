      int a = 5, b = 2;
      double c = 3.0;
      
      a += b;
      b++;
      c--;
      c *= a;
      System.out.println("a + b + c = " + (a + b + c));


Line_____a_____b_____c______output__
  1      5     2     ?        ?
  2      5     2     3.0      ?
  3      5     2     3.0      ?
  4      7     2     3.0      ?
  5      7     3     3.0      ?
  6      7     3     2.0      ?
  7      7     3     14.0     ?
  8      7     3     14.0     a + b + c = 24.0