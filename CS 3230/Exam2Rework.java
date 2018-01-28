import java.lang.*;
import java.util.*;

class Exam2Rework{
   public static void main(String[] args){
      ArrayList<Integer> numbers = new ArrayList<>();
      Random rand = new Random(); 
      
      for(int i=0; i<10; i++){
      	numbers.add(rand.nextInt(100));
      }
      
      numbers.forEach(number -> System.out.println(number));   
   }
}