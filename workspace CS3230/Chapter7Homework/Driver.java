package brooksNathan;

public class Driver {

	public static void main(String[] args) {
		System.out.println("Converting 324.24 to english:");
		System.out.println(Converter.convertCheck("324.24"));
		System.out.println(Converter.convertCheck("1234.01"));
		
		System.out.println("\n\nConverting 3 * (2 + 5) to postfix notation:");
		System.out.println(Converter.convertInfixToPostfix("3 * (2 + 5)"));
	}
}
