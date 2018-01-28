#include<iostream>
using namespace std;
int main()
{
	int first_number;
	int second_number;
	int gcd;
	int done = 0;
	char yesOrNo;

	cout << "This program will find the greatest common denominator between 2 numbers\n";

	while (done == 0)
		{
			cout << "Enter First Number : ";
			cin >> first_number;

			cout << "Enter Second Number: ";
			cin >> second_number;

			for(int i = 1; i <= first_number && i <= second_number; i++)
			{

				if(first_number % i == 0 && second_number % i == 0 )
				{
					gcd = i;
				}
			}

			cout << "Greatest Common Divison (GCD):" << gcd << endl;
			cout << "Continue? y/n: ";
			cin >> yesOrNo;
			cout << "\n";

			if (yesOrNo == 'n')
			{
				done = 1;
			}
		}

	return 0;

}
