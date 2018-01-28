package sample;

import java.util.ArrayList;

public class StringCalculator {
	public static int add(String string){
		int value = 0;
		
		String[] values = string.split(",");
		
		if(string.length() == 0){
			return 0;
		}
		
		else if(values.length > 2){
			throw new RuntimeException("Don't pass in more than 2 values!!!!");
		}
		else{
			for(int i=0; i<values.length; i++){
				if(Character.isDigit(values[i].charAt(0))){
					value += Integer.parseInt(values[i]);
				}
				else{
					throw new RuntimeException("Don't enter non-numeric characters!!!");
				}
			}
		}
		return value;
	}
}
