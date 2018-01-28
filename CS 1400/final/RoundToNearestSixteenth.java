class RoundToNearestSixteenth
{
   public static void main(String args[])
   {
      for(double i=1; i<33; i++)
      {
         if(i < 10)
         {
            System.out.println(" " + (int)i + "/32 : " + i/32);

         }
         else
         {
            System.out.println((int)i + "/32 : " + i/32);
         }   
      }
   }
}