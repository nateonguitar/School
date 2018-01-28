import java.util.*;

public class Deck
{
   ArrayList< Card > cardPile;
   ArrayList< Card > shuffledCardPile;
   ArrayList< Integer > unsortedBin;
   ArrayList< Integer > sortedBin;
   Random ran = new Random();
   Scanner input = new Scanner( System.in );
   
   public Deck()
   {
      cardPile = new ArrayList< Card >();
      
      for( int a = 0; a < 4; a++ )
      {
         for( int b = 0; b < 13; b++ )
         {
            cardPile.add( new Card( a, b ) );
         }
      }
   }
   
   private int[] shuffleToolMaker()
   {  
      int[] shuffleTool = new int[ 52 ];
      ArrayList< Integer > unsortedBin = new ArrayList< Integer >();
      ArrayList< Integer > sortedBin = new ArrayList< Integer >();
      Random rand = new Random();
      
      for( int i = 0; i < 52; i++ )
      {
        sortedBin.add(i, i);
      }
      
      while( sortedBin.size() != 0 )
      {
         int randomEntry = rand.nextInt( sortedBin.size() );
         unsortedBin.add( sortedBin.get( randomEntry )  );
         sortedBin.remove( randomEntry );
      }
      
      for( int i = 0; i < unsortedBin.size(); i++ )
      {
         shuffleTool[ i ] = unsortedBin.get( i );
      }
      return shuffleTool;
   }
   
   public void shuffle()
   {
      ArrayList< Card > shuffledCardPile = new ArrayList< Card >();
      int[] randomNumberList = new int [ 52 ];
      randomNumberList = shuffleToolMaker();
      
      for( int a = 0; a < randomNumberList.length - 1; a++ )
      {
         shuffledCardPile.add( cardPile.get( randomNumberList[ a ] ) );
      }
   }
   
   public void deal( int n, boolean up ) //eventually replace void with ArrayList
   {  
   
   }
   
   public int getDeckSize()
   {
      return cardPile.size();
   }  
   
   public String toString()
   {  
      
      String list = "";
		for( int i = 0; i < cardPile.size(); i++ )
		{
			list += cardPile.get( i ).toString();
		}
		return list;
   }
}