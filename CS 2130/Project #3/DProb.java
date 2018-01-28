// Discrete Probability Library
public class DProb
{
   public static double Permutations(long N, long X)
   {
      double perm = 1.0;
      
      // P(N, X) = (N!)/((N-X)!)
      for(long i=0; N-i > N-X; i++)
      {
          perm *= (double)(N-i);
      }

      return perm;
   }
   
   //------------------------------------------------------
   
   public static double Combinations(long N, long X)
   {
      double comb = 1.0;
      
      long numLoops = 0;
      
      if(X == 0 || X == N)
      {
         return comb;
      }
      
      // figure out which of the two versions can be reduced more // 12650
      if(X > (N-X))
      {
         numLoops = N-X;
      }
      else 
      {
         numLoops = N - (N-X);
      }
           
            
      for(long i=1; i<=numLoops; i++)
      {
          comb *= (double) ( (N-i) + 1) / (i);
      }
      
      return comb;
   }
   
   //------------------------------------------------------

   public static double HyperGeometric(long Np, long Xp, long N, long X)
   {
      double probX = 1.0;
      
      probX *= Combinations(Xp, X)*Combinations(Np-Xp, N-X)  / Combinations(Np, N);
      
      return probX;
   }
   
   //------------------------------------------------------
   


   public static double Binomial(double P, long N, long X)
   {
      double probX = 1.0;
      
      probX *= Combinations(N, X) * Math.pow(P,X) * Math.pow((1-P),(N-X));
      
      return probX;
   }
   
     
   //------------------------------------------------------
   
   public static double Poisson(double Xmean, long X)
   {
      double probX = 1.0;
      
      probX *= Math.pow(Math.E, -Xmean) * Math.pow(Xmean, X);
     
      double denominator = (double) X;
      
      while(X > 1)
      {
         denominator *= (X - 1);
         X--;
      }
      
      probX /= denominator;
      
      
      return probX;
   }
} // end class