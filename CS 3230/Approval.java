package brooksNathan;

import java.util.Scanner;

public class Approval {
	private static double populationMean;
	private double mean;
	private static double maxValue;
	private static double minValue;
	private double variance = 0.0;
	private static double sample;
	private static double where;
	private static int numSamples;
	private static Scanner in = new Scanner(System.in);

	public void doPoll(int samples){
		double sampleTotal = 0;
		this.numSamples = samples;
		for(int i=0; i<samples; i++){
			sampleTotal += getSample();
		}
		mean = sampleTotal / samples;
	}
	
	public double getMean(){
		return this.mean;
	}
	
	public double getVariance(){	
		// variance = <ValueSquared> – <Value> * <Value>.
		// variance = <(x – x)2>
		this.variance = (this.mean*this.mean); //  (x-x)
		this.variance /= numSamples;
		this.variance -= (this.mean/numSamples) * (this.mean/numSamples);
		
		return this.variance;
	}
	
	public static void setPopulationMean(){
		populationMean = Math.random();
	}
	
	public static double getPopulationMean(){
		return populationMean;
	}
	
	public static double getSample(){
		sample = Math.random();
		where = Math.random();
		
		if (where < populationMean)
		{
  			sample = populationMean + (1 - populationMean) * sample;
		}
		else
		{
  			sample = populationMean - populationMean * sample;
		}
		return sample;
	}

	
	/*
	The verifyModel method establishes an independent population mean, 
	and loops through one million samples, to confirm that the average 
	of all of them approximately equals the established population mean, 
	and to confirm that all samples are within the allowed range. 
	*/
	public static void verifyModel() {
		
		// establish an independent population mean
		setPopulationMean();
		
		// loop through 1 million samples to confirm that the average 
		// of all them approximately equals the established population mean
		numSamples = 1000000;
		double sampleTotal = 0;
		
		maxValue = 0;
		minValue = 1;
		
		for(int i=0; i<numSamples; i++){
			sampleTotal += getSample();
			if(sample > maxValue){
				maxValue = sample;
			}
			if(sample < minValue){
				minValue = sample;
			}
		}
		
		double meanx = sampleTotal / numSamples;
		
		System.out.println("populationMean = %" + populationMean + " for " + numSamples + " samples");
		System.out.println("maxValue = " + maxValue);
		System.out.println("minValue = " + minValue);
		System.out.println("avgValue = " + meanx + "\n-----------------------------\n");
	}
}

