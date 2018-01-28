package brooksNathan;

public enum Numbers{
	ACE  (1,  1,  11, 12, "Ace"		),
	TWO  (2,  2,  2,  2 , "Two"		),
	THREE(3,  3,  3,  3 , "Three"	),
	FOUR (4,  4,  4,  4 , "Four"	),
	FIVE (5,  5,  5,  5 , "Five"	),
	SIX  (6,  6,  6,  6 , "Six"		),
	SEVEN(7,  7,  7,  7 , "Seven"	),
	EIGHT(8,  8,  8,  8 , "Eight"	),
	NINE (9,  9,  9,  9 , "Nine"	),
	TEN  (10, 10, 10, 10, "Ten"		),
	JACK (11, 10, 10, 11, "Jack"	),
	QUEEN(12, 10, 10, 11, "Queen"	),
	KING (13, 10, 10, 11, "King"	);
	
	
	private int number;
	private int value;
	private int alternate;
	private int weight;
	private String faceValue;
	
	//-----------------------------------------------------------------------------
	
	private Numbers(int number, int value, int alternate, int weight, String faceValue) {
		this.number = number;
		this.value = value;
		this.alternate = alternate;
		this.weight = weight;
		this.faceValue = faceValue;
	}
	
	//-----------------------------------------------------------------------------
	
	public int number() 		{ return number; 	}
	public int value()			{ return value;		}
    public int alternate() 		{ return alternate; }
    public int weight()			{ return weight; 	}
    public String faceValue()	{ return faceValue;	}
};
