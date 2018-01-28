package com.nateonguitar.obliquetrianglesolver;

import com.nateonguitar.obliquetrianglesolver.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	double[][] values = new double[2][3];

	double[][] values2 = new double[2][3];

	double temp;

	boolean secondTrianglePossible = false;

	double possibleSecondAngleValue = 0;

	boolean problems = false;

	int count = 0;
	boolean emptySlots = true;

	int sidesDone = 0;
	int anglesDone = 0;

	double possibleOtherAngle = 0;
	double possibleOtherSide = 0;

	double pi = Math.PI;

	int index;

	int test = 0;

	private EditText sideA, sideB, sideC, alpha, beta, gamma;
	private TextView sideA1, sideA2, sideB1, sideB2, sideC1, sideC2, alpha1,
			alpha2, beta1, beta2, gamma1, gamma2, errors;
	private LinearLayout triangle2area;

	private Button calc, reset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// /////////////////////////////////////
		// set up buttons, inputs, and various
		// /////////////////////////////////////

		sideA = (EditText) findViewById(R.id.sideA);
		sideB = (EditText) findViewById(R.id.sideB);
		sideC = (EditText) findViewById(R.id.sideC);
		alpha = (EditText) findViewById(R.id.alpha);
		beta = (EditText) findViewById(R.id.beta);
		gamma = (EditText) findViewById(R.id.gamma);

		sideA1 = (TextView) findViewById(R.id.sideA1);
		sideB1 = (TextView) findViewById(R.id.sideB1);
		sideC1 = (TextView) findViewById(R.id.sideC1);
		alpha1 = (TextView) findViewById(R.id.alpha1);
		beta1 = (TextView) findViewById(R.id.beta1);
		gamma1 = (TextView) findViewById(R.id.gamma1);
		
		triangle2area = (LinearLayout) findViewById(R.id.triangle2area);

		sideA2 = (TextView) findViewById(R.id.sideA2);
		sideB2 = (TextView) findViewById(R.id.sideB2);
		sideC2 = (TextView) findViewById(R.id.sideC2);
		alpha2 = (TextView) findViewById(R.id.alpha2);
		beta2 = (TextView) findViewById(R.id.beta2);
		gamma2 = (TextView) findViewById(R.id.gamma2);

		errors = (TextView) findViewById(R.id.errors);

		calc = (Button) findViewById(R.id.calcButton);
		reset = (Button) findViewById(R.id.resetButton);

		calc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				calculate();
			}
		});

		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				resetAnswers();
				resetValues();
				ScrollView sv = (ScrollView) findViewById(R.id.mainview);
				sv.smoothScrollTo(0, sv.getTop());
			}

		});
	}

	// create a multi dimensional array

	private void calculate() {

		resetAnswers();

		// sides

		// side a
		if (!sideA.getText().toString().equals("")) {
			temp = Double.parseDouble(sideA.getText().toString());
			values[0][0] = temp;
			values2[0][0] = temp;
		} else {
			values[0][0] = -1;
			values2[0][0] = -1;
		}

		// side b
		if (!sideB.getText().toString().equals("")) {
			temp = Double.parseDouble(sideB.getText().toString());
			values[0][1] = temp;
			values2[0][1] = temp;
		} else {
			values[0][1] = -1;
			values2[0][1] = -1;
		}

		// side c
		if (!sideC.getText().toString().equals("")) {
			temp = Double.parseDouble(sideC.getText().toString());
			values[0][2] = temp;
			values2[0][2] = temp;
		} else {
			values[0][2] = -1;
			values2[0][2] = -1;
		}

		// angles

		// angle alpha
		if (!alpha.getText().toString().equals("")) {
			temp = Double.parseDouble(alpha.getText().toString());
			values[1][0] = temp;
			values2[1][0] = temp;
		} else {
			values[1][0] = -1;
			values2[1][0] = -1;
		}

		// angle beta
		if (!beta.getText().toString().equals("")) {
			temp = Double.parseDouble(beta.getText().toString());
			values[1][1] = temp;
			values2[1][1] = temp;
		} else {
			values[1][1] = -1;
			values2[1][1] = -1;
		}

		// angle gamma
		if (!gamma.getText().toString().equals("")) {
			temp = Double.parseDouble(gamma.getText().toString());
			values[1][2] = temp;
			values2[1][2] = temp;
		} else {
			values[1][2] = -1;
			values2[1][2] = -1;
		}

		count = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (values[i][j] != -1) {
					count++;
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			if (values[0][i] != -1) {
				sidesDone++;
			}
			if (values[1][i] != -1) {
				anglesDone++;
			}
		}

		if (count < 3) {
			errors.setText("You need to give 3 pieces of information to calculate");
			problems = true;
		} else if (count > 3) {
			errors.setText("Please only give 3 values");
			problems = true;
		} else if (values[0][0] == -1 && values[0][1] == -1
				&& values[0][2] == -1) {
			errors.setText("Infinite solutions, must have at least 1 side value");
			problems = true;
		} else if (values[1][0] != -1 && values[1][1] != -1
				&& values[1][2] != -1
				&& (values[1][0] + values[1][1] + values[1][2]) != 180) {
			errors.setText("You gave all 3 angles, they need to add up to 180 or this isn't a triangle");
			problems = true;
		} else if (values[1][0] == 90 || values[1][1] == 90
				|| values[1][2] == 90) {
			errors.setText("This is not a right triangle solver, don't use 90 degrees");
			problems = true;
		}

		else {
			calcValues(values);

			sidesDone = 0;
			anglesDone = 0;

			if (secondTrianglePossible == true) {
				anglesDone = 2;
				sidesDone = 2;
				calcValues(values2);
			}
			
			
			
			
			
			
			// used if I decide to ever allow the user to give more than 3 values 
			/*
			if (Double.isNaN(values[0][0]) || Double.isNaN(values[0][1])
					|| Double.isNaN(values[0][2]) || Double.isNaN(values[1][0])
					|| Double.isNaN(values[1][1]) || Double.isNaN(values[1][2])) {
				
				problems = true;
			}
			*/
			
			
			
			// does the same thing as ^ if statement, just more efficiently
			/*
			for(int i=0; i<2; i++){
				for(int j=0; j<3; j++){
					if(Double.isNaN(values[i][j])){
						errors.setText("Not a triangle");
						problems = true;
						i = 2;
						j = 3;
					}
				}
			}
			*/
			
			if (problems == false) {
				sideA1.setText(" " + values[0][0]);
				sideB1.setText(" " + values[0][1]);
				sideC1.setText(" " + values[0][2]);
				alpha1.setText(" " + values[1][0] + "\u00B0");
				beta1.setText(" " + values[1][1] + "\u00B0");
				gamma1.setText(" " + values[1][2] + "\u00B0");

				ScrollView sv = (ScrollView) findViewById(R.id.mainview);
				sv.smoothScrollTo(0, sv.getBottom());

				errors.setText("");

				if (secondTrianglePossible == true) {
					triangle2area.setVisibility(View.VISIBLE);
					sideA2.setText(" " + values2[0][0]);
					sideB2.setText(" " + values2[0][1]);
					sideC2.setText(" " + values2[0][2]);
					alpha2.setText(" " + values2[1][0] + "\u00B0");
					beta2.setText(" " + values2[1][1] + "\u00B0");
					gamma2.setText(" " + values2[1][2] + "\u00B0");

					errors.setText("");
				}
			}
		}
		
		if(problems == true){
			ScrollView sv = (ScrollView) findViewById(R.id.mainview);
			sv.smoothScrollTo(0, 50);
		}
		// reset all values
		// this allows someone to fill it out again without refreshing
		secondTrianglePossible = false;
	}

	// ////////////////////////////////
	// calcValues /////////////////////
	// ////////////////////////////////
	private void calcValues(double givenValues[][]) {

		// set emptySlots to false, if there are any empty slots, change it back
		// to true
		emptySlots = false;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (givenValues[i][j] == -1) {
					emptySlots = true;
				}
			}
		}

		if (emptySlots) {

			// solve missing angle if 2 angles exist
			if (anglesDone == 2) {
				if (givenValues[1][0] == -1) {
					givenValues[1][0] = (double) Math
							.floor((180 - (givenValues[1][1] + givenValues[1][2])) * 1000) / 1000;
				} else if (givenValues[1][1] == -1) {
					givenValues[1][1] = (double) Math
							.floor((180 - (givenValues[1][0] + givenValues[1][2])) * 1000) / 1000;
				} else {
					givenValues[1][2] = (double) Math
							.floor((180 - (givenValues[1][0] + givenValues[1][1])) * 1000) / 1000;
				}
				anglesDone++;
			}

			else if ((givenValues[1][0] + givenValues[1][1] + givenValues[1][2]) > 180.003) {
				errors.setText("That is not a triangle, angles add up to more than 180 degrees");
				problems = true;
			}

			// sides don't make a triangle
			// case a + b < c
			else if ((givenValues[0][0] + givenValues[0][1]) <= givenValues[0][2]
					&& givenValues[0][0] != -1
					&& givenValues[0][1] != -1
					&& givenValues[0][2] != -1) {
				errors.setText("sides don't make a triangle");
				problems = true;
			}

			// case a + c < b
			else if ((givenValues[0][0] + givenValues[0][2]) <= givenValues[0][1]
					&& givenValues[0][0] != -1
					&& givenValues[0][1] != -1
					&& givenValues[0][2] != -1) {
				errors.setText("sides don't make a triangle");
				problems = true;
			}

			// case b + c < a
			else if ((givenValues[0][1] + givenValues[0][2]) <= givenValues[0][0]
					&& givenValues[0][0] != -1
					&& givenValues[0][1] != -1
					&& givenValues[0][2] != -1) {
				errors.setText("sides don't make a triangle");
				problems = true;
			}

			// ///////////////
			// angles law of sines
			// ///////////////

			// case b beta a solve for alpha
			else if (givenValues[0][0] != -1 && givenValues[1][1] != -1
					&& givenValues[0][1] != -1 && givenValues[1][0] == -1) {
				givenValues[1][0] = lawOfSines1Angle2Sides(givenValues[0][1],
						givenValues[1][1], givenValues[0][0]);
				anglesDone++;

				// if c, gamma, and alpha2 are empty
				if (values[0][2] == -1 && values[1][2] == -1
						&& values2[1][0] == -1) {
					// check if second value is a valid triangle
					possibleSecondAngleValue = 180 - values[1][0];

					// if we don't get a negative when tested
					if (180 - (possibleSecondAngleValue + values[1][1]) > 0) {
						secondTrianglePossible = true;
						// set the alternate alpha
						values2[1][0] = possibleSecondAngleValue;
					}
				}
			}

			// case c gamma a solve for alpha
			else if (givenValues[0][0] != -1 && givenValues[1][2] != -1
					&& givenValues[0][2] != -1 && givenValues[1][0] == -1) {
				givenValues[1][0] = lawOfSines1Angle2Sides(givenValues[0][2],
						givenValues[1][2], givenValues[0][0]);
				anglesDone++;

				// if b, beta, and alpha2 are empty
				if (values[0][1] == -1 && values[1][1] == -1
						&& values2[1][0] == -1) {
					// check if second value is a valid triangle
					possibleSecondAngleValue = 180 - values[1][0];

					// if we don't get a negative when tested
					if (180 - (possibleSecondAngleValue + values[1][2]) > 0) {
						secondTrianglePossible = true;
						// set the alternate alpha
						values2[1][0] = possibleSecondAngleValue;
					}
				}
			}

			// case a alpha b solve for beta
			else if (givenValues[0][0] != -1 && givenValues[1][0] != -1
					&& givenValues[0][1] != -1 && givenValues[1][1] == -1) {
				givenValues[1][1] = lawOfSines1Angle2Sides(givenValues[0][0],
						givenValues[1][0], givenValues[0][1]);
				anglesDone++;

				// if c, gamma, and beta2 are empty
				if (values[0][2] == -1 && values[1][2] == -1
						&& values2[1][1] == -1) {
					// check if second value is a valid triangle
					possibleSecondAngleValue = 180 - values[1][1];

					// if we don't get a negative when tested
					if (180 - (possibleSecondAngleValue + values[1][0]) > 0) {
						secondTrianglePossible = true;
						// set the alternate beta
						values2[1][1] = possibleSecondAngleValue;
					}
				}
			}

			// case c gamma b solve for beta
			else if (givenValues[0][2] != -1 && givenValues[1][2] != -1
					&& givenValues[0][1] != -1 && givenValues[1][1] == -1) {
				givenValues[1][1] = lawOfSines1Angle2Sides(givenValues[0][2],
						givenValues[1][2], givenValues[0][1]);
				anglesDone++;

				// if a, alpha, and beta2 are empty
				if (values[0][0] == -1 && values[1][0] == -1
						&& values2[1][1] == -1) {
					// check if second value is a valid triangle
					possibleSecondAngleValue = 180 - values[1][1];

					// if we don't get a negative when tested
					if (180 - (possibleSecondAngleValue + values[1][2]) > 0) {
						secondTrianglePossible = true;
						// set the alternate beta
						values2[1][1] = possibleSecondAngleValue;
					}
				}
			}
			// problem area
			// case b beta c solve for gamma
			else if (givenValues[0][1] != -1 && givenValues[1][1] != -1
					&& givenValues[0][2] != -1 && givenValues[1][2] == -1) {
				givenValues[1][2] = lawOfSines1Angle2Sides(givenValues[0][1],
						givenValues[1][1], givenValues[0][2]);
				anglesDone++;

				// if a, alpha, and gamma2 are empty
				if (values[0][0] == -1 && values[1][0] == -1
						&& values2[1][2] == -1) {
					// check if second value is a valid triangle
					possibleSecondAngleValue = 180 - values[1][2];

					// if we don't get a negative when tested
					if (180 - (possibleSecondAngleValue + values[1][1]) > 0) {
						secondTrianglePossible = true;
						// set the alternate gamma
						values2[1][2] = possibleSecondAngleValue;
					}
				}
			}

			// case a alpha c solve for gamma
			else if (givenValues[0][0] != -1 && givenValues[1][0] != -1
					&& givenValues[0][2] != -1 && givenValues[1][2] == -1) {
				givenValues[1][2] = lawOfSines1Angle2Sides(givenValues[0][0],
						givenValues[1][0], givenValues[0][2]);
				anglesDone++;

				// if b, beta, and gamma2 are empty
				if (values[0][1] == -1 && values[1][1] == -1
						&& values2[1][2] == -1) {
					// check if second value is a valid triangle
					possibleSecondAngleValue = 180 - values[1][2];

					// if we don't get a negative when tested
					if (180 - (possibleSecondAngleValue + values[1][0]) > 0) {
						secondTrianglePossible = true;
						// set the alternate gamma
						values2[1][2] = possibleSecondAngleValue;
					}
				}
			}

			// //////////////////
			// sides law of sines
			// //////////////////
			// case b beta alpha solve for a
			else if (givenValues[0][1] != -1 && givenValues[1][1] != -1
					&& givenValues[1][0] != -1 && givenValues[0][0] == -1) {
				givenValues[0][0] = lawOfSines1Side2Angles(givenValues[0][1],
						givenValues[1][1], givenValues[1][0]);
				sidesDone++;
			}

			// case c gamma alpha solve for a
			else if (givenValues[0][2] != -1 && givenValues[1][2] != -1
					&& givenValues[1][0] != -1 && givenValues[0][0] == -1) {
				givenValues[0][0] = lawOfSines1Side2Angles(givenValues[0][2],
						givenValues[1][2], givenValues[1][0]);
				sidesDone++;
			}

			// case a alpha beta solve for b
			else if (givenValues[0][0] != -1 && givenValues[1][0] != -1
					&& givenValues[1][1] != -1 && givenValues[0][1] == -1) {
				givenValues[0][1] = lawOfSines1Side2Angles(givenValues[0][0],
						givenValues[1][0], givenValues[1][1]);
				sidesDone++;
			}

			// case c gamma beta solve for b
			else if (givenValues[0][2] != -1 && givenValues[1][2] != -1
					&& givenValues[1][1] != -1 && givenValues[0][1] == -1) {
				givenValues[0][1] = lawOfSines1Side2Angles(givenValues[0][2],
						givenValues[1][2], givenValues[1][1]);
				sidesDone++;
			}

			// case a alpha gamma solve for c
			else if (givenValues[0][0] != -1 && givenValues[1][0] != -1
					&& givenValues[1][2] != -1 && givenValues[0][2] == -1) {
				givenValues[0][2] = lawOfSines1Side2Angles(givenValues[0][0],
						givenValues[1][0], givenValues[1][2]);
				sidesDone++;
			}

			// case b beta gamma solve for c
			else if (givenValues[0][1] != -1 && givenValues[1][1] != -1
					&& givenValues[1][2] != -1 && givenValues[0][2] == -1) {
				givenValues[0][2] = lawOfSines1Side2Angles(givenValues[0][1],
						givenValues[1][1], givenValues[1][2]);
				sidesDone++;
			}

			// ///////////////////////////
			// sides law of cosines
			// ///////////////////////////

			// case only given sides
			else if (givenValues[1][0] == -1 && givenValues[1][1] == -1
					&& givenValues[1][2] == -1) {
				// case if a is largest
				if (givenValues[0][0] > givenValues[0][1]
						&& givenValues[0][0] > givenValues[0][2]) {
					// solve for alpha
					givenValues[1][0] = lawOfCosinesFindAngle(
							givenValues[0][0], givenValues[0][1],
							givenValues[0][2]);
					anglesDone++;
				}
				// case if b is largest
				else if (givenValues[0][1] > givenValues[0][0]
						&& givenValues[0][1] > givenValues[0][2]) {
					// solve for beta
					givenValues[1][1] = lawOfCosinesFindAngle(
							givenValues[0][1], givenValues[0][0],
							givenValues[0][2]);
					anglesDone++;
				}
				// case if c is largest
				else if (givenValues[0][2] > givenValues[0][0]
						&& givenValues[0][2] > givenValues[0][1]) {
					// solve for gamma
					givenValues[1][2] = lawOfCosinesFindAngle(
							givenValues[0][2], givenValues[0][0],
							givenValues[0][1]);
					anglesDone++;
				}
				// case if all sides are the same length
				else if (givenValues[0][0] == givenValues[0][1]
						&& givenValues[0][1] == givenValues[0][2]) {
					givenValues[1][0] = 60;
					givenValues[1][1] = 60;
					givenValues[1][2] = 60;

					anglesDone += 3;
				}
				// ////////////////////////
				// if 2 sides are the same
				// ////////////////////////

				// case a and b are the same and c is smaller
				else if (givenValues[0][0] == givenValues[0][1]
						&& givenValues[0][0] > givenValues[0][2]) {
					givenValues[1][0] = lawOfCosinesFindAngle(
							givenValues[0][0], givenValues[0][1],
							givenValues[0][2]);
					anglesDone++;
				}

				// case a and b are the same and c is bigger
				else if (givenValues[0][0] == givenValues[0][1]
						&& givenValues[0][0] < givenValues[0][2]) {
					givenValues[1][2] = lawOfCosinesFindAngle(
							givenValues[0][2], givenValues[0][0],
							givenValues[0][1]);
					anglesDone++;
				}

				// case a and c are the same and b is smaller
				else if (givenValues[0][0] == givenValues[0][2]
						&& givenValues[0][0] > givenValues[0][1]) {
					givenValues[1][0] = lawOfCosinesFindAngle(
							givenValues[0][0], givenValues[0][1],
							givenValues[0][2]);
					anglesDone++;
				}

				// case a and c are the same and b is bigger
				else if (givenValues[0][0] == givenValues[0][2]
						&& givenValues[0][0] < givenValues[0][1]) {
					givenValues[1][1] = lawOfCosinesFindAngle(
							givenValues[0][1], givenValues[0][0],
							givenValues[0][2]);
					anglesDone++;
				}

				// case b and c are the same and a is smaller
				else if (givenValues[0][1] == givenValues[0][2]
						&& givenValues[0][1] > givenValues[0][0]) {
					givenValues[1][1] = lawOfCosinesFindAngle(
							givenValues[0][1], givenValues[0][0],
							givenValues[0][2]);
					anglesDone++;
				}

				// case b and c are the same and a is bigger
				else if (givenValues[0][1] == givenValues[0][2]
						&& givenValues[0][1] < givenValues[0][0]) {
					givenValues[1][0] = lawOfCosinesFindAngle(
							givenValues[0][0], givenValues[0][1],
							givenValues[0][2]);
					anglesDone++;
				}
			}

			// //////////////////////////////////////////////////////
			// 2 sides and 1 angle, no opposites to use law of cosines
			// //////////////////////////////////////////////////////

			// case a b gamma given find c
			else if (givenValues[0][0] != -1 && givenValues[0][1] != -1
					&& givenValues[1][2] != -1 && givenValues[0][2] == -1) {
				givenValues[0][2] = lawOfCosinesFindSide(givenValues[0][0],
						givenValues[0][1], givenValues[1][2]);
				sidesDone++;
			}

			// case a c beta given find b
			else if (givenValues[0][0] != -1 && givenValues[0][2] != -1
					&& givenValues[1][1] != -1 && givenValues[0][1] == -1) {
				givenValues[0][1] = lawOfCosinesFindSide(givenValues[0][0],
						givenValues[0][2], givenValues[1][1]);
				sidesDone++;
			}

			// case b c alpha given find a
			else if (givenValues[0][1] != -1 && givenValues[0][2] != -1
					&& givenValues[1][0] != -1 && givenValues[0][0] == -1) {
				givenValues[0][0] = lawOfCosinesFindSide(givenValues[0][1],
						givenValues[0][2], givenValues[1][0]);
				sidesDone++;
			}

			// run again to check for empty slots then calc again
			if ((sidesDone + anglesDone) < 6 && problems == false) {
				calcValues(givenValues);
			}
		}
	}

	// want to find the smallest angle first
	private double lawOfSines1Side2Angles(double side1, double angle1,
			double angle2) {
		double side2 = (side1 * Math.sin(angle2 * pi / 180))
				/ Math.sin(angle1 * pi / 180);
		return (double) Math.floor(side2 * 1000) / 1000;
	}

	private double lawOfSines1Angle2Sides(double side1, double angle1,
			double side2) {
		double angle2 = (Math.asin((side2 * Math.sin(angle1 * (pi / 180)))
				/ side1))
				* (180 / pi);
		return (double) Math.floor(angle2 * 1000) / 1000;
	}

	// want to find the biggest side first (a is biggest)
	private double lawOfCosinesFindAngle(double biggestSide, double b, double c) {
		// alpha = acos((a^2 - b^2 - c^2) / (-2ab))

		double angle = (Math
				.acos(((biggestSide * biggestSide) - (b * b) - (c * c))
						/ (-2 * b * c)) * (180 / pi));
		return (double) Math.floor(angle * 1000) / 1000;
	}

	private double lawOfCosinesFindSide(double firstSideGiven,
			double secondSideGiven, double givenAngle) {
		// for cases with 2 sides and 1 angle given, no opposites to use law of
		// sines
		// a^2 = b^2 + c^2 -2ab(cos(alpha))

		// might need working, not fully tested
		double unknownSide = Math.sqrt((firstSideGiven * firstSideGiven)
				+ (secondSideGiven * secondSideGiven) - 2 * firstSideGiven
				* secondSideGiven * (Math.cos(givenAngle) * (pi / 180)));
		return (double) Math.floor(unknownSide * 1000) / 1000;
	}

	private void resetValues() {
		sideA.setText("");
		sideB.setText("");
		sideC.setText("");
		alpha.setText("");
		beta.setText("");
		gamma.setText("");
	}

	private void resetAnswers() {
		sideA1.setText("");
		sideB1.setText("");
		sideC1.setText("");
		alpha1.setText("");
		beta1.setText("");
		gamma1.setText("");

		sideA2.setText("");
		sideB2.setText("");
		sideC2.setText("");
		alpha2.setText("");
		beta2.setText("");
		gamma2.setText("");

		triangle2area.setVisibility(View.INVISIBLE);
		errors.setText("");

		problems = false;

		sidesDone = 0;
		anglesDone = 0;

		secondTrianglePossible = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
