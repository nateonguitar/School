//Copyright 2015, Bradley Peterson, Weber State University, all rights reserved.

#include <iostream>
#include <string>
#include <stack>
#include <sstream>
#include <algorithm> 
#include <functional> 
#include <cctype>
#include <locale>


using namespace std;

string convertToPfx(const string& infx) {
    string pfx = "";
    stack<string> s;
    string sym = "";
    
    unsigned int position = 0;
	int breaker = 0;

    while(position < infx.size()) {
		sym = infx.at(position);
		// build the symbol

		// if there are spaces, skip them
		while(infx.at(position) == ' ')
		{
			position ++;
			sym = infx.at(position);
		}

		// position could have moved so we need to check again
		// check if there is another digit after a digit
		while (position < infx.size() && isdigit(infx.at(position)))
		{
			
			

			// don't check if infx.at(position+1) is out of bounds
			while( !(position == (infx.size()-1) ) )
			{
				if(isdigit(infx.at(position + 1)))
				{
					sym += infx.at(position + 1);
					position++;
				}
				else
				{
					break;
				}
			}

			if(breaker == 1)
			{
				break;
			}

			// position could have moved again
			// if position is before the end of the array
			if(position < infx.size() && !(position == (infx.size()-1) )) 
			{
				// if the next space is not a digit - break
				if(!( isdigit(infx.at(position + 1)) ))
				{
					// stop looping
					breaker = 1;
				}
			}
		}		


		// decide what to do depending on what the symbol is
		switch(sym[0])
		{
		case ' ':
			break;

		// b.2
		case '(':
			s.push("(");
			break;

		// b.3
		case ')':
			// move stacked operators to pfx until it runs into a "("
			while(s.top() != "(")
			{
				pfx += s.top() + " ";
				s.pop();
			}

			// delete the "(" when it runs into it
			if(s.top() == "(")
			{
				s.pop();
			}
			break;

		// b.4
		case '^':
			s.push("^");
			break;
		case '*':
			for(unsigned int i=0; i<s.size(); i++)
			{
				if(s.top() == "(")
				{
					break;
				}
				if(s.top() == "^")
				{
					pfx += s.top() + " ";
					s.pop();
				}
			}
			s.push("*");
			break;
		case '/':
			for(unsigned int i=0; i<s.size(); i++)
			{
				if(s.top() == "(")
				{
					break;
				}
				if(s.top() == "^")
				{
					pfx += s.top() + " ";
					s.pop();
				}
			}
			s.push("/");
			break;
		case '+':
			for(unsigned int i=0; i<s.size(); i++)
			{
				if(s.top() == "(")
				{
					break;
				}
				if(s.top() == "^" || s.top() == "*" || s.top() == "/")
				{
					pfx += s.top() + " ";
					s.pop();
				}
			}
			s.push("+");
			break;
		case '-':
			for(unsigned int i=0; i<s.size(); i++)
			{
				if(s.top() == "(")
				{
					break;
				}
				if(s.top() == "^" || s.top() == "*" || s.top() == "/")
				{
					pfx += s.top() + " ";
					s.pop();
				}
			}
			s.push("-");
			break;
	
		// b.1
		default:
			pfx += sym + ' ';
			
			break;
		}

		sym = "";

		position++;
		
    }

	// part c
	while (s.empty() == false)
	{
		if(s.top() == "(")
		{
			s.pop();
		}
		else
		{
			pfx += s.top();
			pfx += ' ';

			s.pop();
		}
	}
    return pfx;
}


//This helps with testing, do not modify.
bool checkTest(string testName, string whatItShouldBe, string whatItIs) 
{

    //get rid of spaces
    whatItIs.erase(whatItIs.begin(), std::find_if(whatItIs.begin(), whatItIs.end(), std::not1(std::ptr_fun<int, int>(std::isspace))));
    whatItIs.erase(std::find_if(whatItIs.rbegin(), whatItIs.rend(), std::not1(std::ptr_fun<int, int>(std::isspace))).base(), whatItIs.end());
            
    if (whatItShouldBe == whatItIs) {
        cout << "Passed test " << testName << " ***  Output was \""<< whatItIs << "\"" << endl;
        return true;
    }
    else { 
        if (whatItShouldBe == "") {
            cout << "***Failed test " << testName << " *** " << endl << "   Output was \""<< whatItIs << "\"" << endl << "  Output should have been blank. " << endl;
        } else {
            cout << "***Failed test " << testName << " *** " << endl << "   Output was \""<< whatItIs << "\"" << endl << "  Output should have been \"" << whatItShouldBe << "\"" << endl;
        }
        return false;
    }
}

int main() {
    
    string expression = "(2+3)";
    string result = convertToPfx(expression);
    checkTest("Test #1", "2 3 +", result);
    
    expression = "2+3";
    result = convertToPfx(expression);
    checkTest("Test #2", "2 3 +", result);

    expression = "(123+456)";
    result = convertToPfx(expression);
    checkTest("Test #3", "123 456 +", result);

    expression = "(8-5)";
    result = convertToPfx(expression);
    checkTest("Test #4", "8 5 -", result);

    expression = "((3-4)-5)";
    result = convertToPfx(expression);
    checkTest("Test #5", "3 4 - 5 -", result);

    expression = "(3 - (4 - 5))";
    result = convertToPfx(expression);
    checkTest("Test #6", "3 4 5 - -", result);

    expression = "(3*(8/2))";
    result = convertToPfx(expression);
    checkTest("Test #7", "3 8 2 / *", result);

    expression = "3 + 8 / 2";
    result = convertToPfx(expression);
    checkTest("Test #8", "3 8 2 / +", result);

    expression = "24 / 3 + 2";
    result = convertToPfx(expression);
    checkTest("Test #9", "24 3 / 2 +", result);

    expression = "((1 + 2) * (3 + 4))";
    result = convertToPfx(expression);
    checkTest("Test #10", "1 2 + 3 4 + *", result);

    expression = "2^3";
    result = convertToPfx(expression);
    checkTest("Test #11", "2 3 ^", result);

    expression = "8 + 3^4";
    result = convertToPfx(expression);
    checkTest("Test #12", "8 3 4 ^ +", result);

    expression = "(((3+12)-7)*120)/(2+3)";
    result = convertToPfx(expression);
    checkTest("Test #13", "3 12 + 7 - 120 * 2 3 + /", result);

    expression = "((((9+(2*(110-(20/2))))*8)+1000)/2)-((400*2500)-1000001)"; 
    result = convertToPfx(expression);
    checkTest("Test #14", "9 2 110 20 2 / - * + 8 * 1000 + 2 / 400 2500 * 1000001 - -", result);



    system("pause");
    return 0;
}