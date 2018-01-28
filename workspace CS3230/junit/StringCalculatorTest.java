package sample;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class StringCalculatorTest {

	@Test
	public final void addTwoNumbersTest() {
		 assertNotEquals(2, StringCalculator.add("3,4"));
		 assertEquals(5, StringCalculator.add("2,3"));
		 assertEquals(3, StringCalculator.add("1,2"));
		 Assert.assertTrue(true);
	}
	
	@Test (expected = RuntimeException.class)
	public final void addNonNumbersTest(){
		StringCalculator.add("2,X");
	}
	
	@Test
	public final void whenAnEmptyStringIsPassed(){
		assertEquals(0, StringCalculator.add(""));
	}
	
	@Test (expected = RuntimeException.class)
	public final void whenMoreThan2ValuesArePassed(){
		StringCalculator.add("2,3,4");
	}
}
