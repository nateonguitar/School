package brooksNathan;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConverterTest {

	@Test
	public void convertCheckTest() {
		assertEquals("Three Hundred Twenty Four Dollars and Twenty Four Cents", Converter.convertCheck("324.24"));
		assertEquals("One Hundred Thirty Five Dollars and One Cents", Converter.convertCheck("135.01"));
		assertEquals("One Thousand Two Hundred Thirty Four Dollars and One Cents", Converter.convertCheck("1234.01"));
	}
	
	@Test (expected = RuntimeException.class)
	public final void convertCheckWhenNotCorrectFormat(){
		Converter.convertCheck("f");
		Converter.convertCheck("123.123.123");
	}
	
	@Test
	public void convertInfixToPostfixTest() {
		assertEquals("3 2 5 + ", Converter.convertInfixToPostfix("3 * (2 + 5)"));
	}

	@Test (expected = RuntimeException.class)
	public final void convertInfixToPostfixBadInputTest(){
		Converter.convertInfixToPostfix("f");
		Converter.convertInfixToPostfix("1 * a");
	}
}
