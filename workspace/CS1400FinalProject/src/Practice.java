import java.util.ArrayList;

public class Practice
{
	public void setShortest()
	{
			// 0 spades, 1 hearts, 2 diamonds, 3 clubs
		int numberOfSpades = 0;
		int numberOfHearts = 0;
		int numberOfDiamonds = 0;
		int numberOfClubs = 0;
		
		shortest = 3;
		
			// count the number of each suit
		for (int i=0; i<getCards().size(); i++)
		{
			switch (getCards().get(i).getSuit())
			{
			case 0:
				numberOfSpades++;
				break;
			
			case 1:
				numberOfHearts++;
				break;
				
			case 2:
				numberOfDiamonds++;
				break;
				
			case 3:
				numberOfClubs++;
				break;
			}
		}
		
		
			// test for aces, kings, or queens in spades
		boolean spadeAce = false;
		boolean spadeKing = false;
		boolean spadeQueen = false;
		
		Card spadeQueenCheck = new Card(0, 12);
		Card spadeKingCheck = new Card(0, 13);
		Card spadeAceCheck = new Card(0, 14);
		
		for (int i=0; i<getCards().size(); i++)
		{
			if(getCards().get(i) == spadeQueenCheck)
			{
				spadeQueen = true;
			}
			if(getCards().get(i) == spadeKingCheck)
			{
				spadeKing = true;
			}
			if(getCards().get(i) == spadeAceCheck)
			{
				spadeAce = true;
			}
			
		}
	}	
}

