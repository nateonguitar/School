public class RandInt
{   
   public int randInt()
   {
         // create a random number between 1 and 36
      Random randGenerator = new Random();
      
      randInteger = randGenerator.nextInt(36);
      return randInteger;
   } // end randInt()
}