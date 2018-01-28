#include<iostream>
#include<conio.h>
#include<string>
#include<deque>

using namespace std;

class Fraction{
	friend std::ostream& operator<<(std::ostream& out, const Fraction& obj);
public:
	Fraction(int numerator, int denominator);
	Fraction mult(const Fraction& rightOperand);
	Fraction add(const Fraction& rightOperand);
	Fraction operator+(const Fraction& rightOperand);
	void print();
private:
	int numerator;
	int denominator;
};

Fraction::Fraction(int numerator, int denominator){
	this->numerator = numerator;
	this->denominator = denominator;
}

Fraction Fraction::mult(const Fraction& rightOperand){
	int newNumerator = this->numerator * rightOperand.numerator;
	int newDenominator = this->denominator * rightOperand.denominator;

	Fraction temp(newNumerator, newDenominator);

	return temp;
}

Fraction Fraction::add(const Fraction& rightOperand){
	int newNumerator = rightOperand.denominator * this->numerator + rightOperand.denominator * this->denominator;
	int newDenominator = this->denominator * rightOperand.denominator;

	Fraction temp(newNumerator, newDenominator);

	return temp;
}

Fraction Fraction::operator+(const Fraction& rightOperand){
	return this->add(rightOperand);
}

void Fraction::print(){
	cout << numerator << "/" << denominator << endl;
}

// overloading the << operator
std::ostream& operator <<(std::ostream& out, const Fraction& obj){
	out << obj.numerator << "/" << obj.denominator << endl;
	return out;
}

int main(){
	Fraction a(2, 5);
	Fraction b(4, 3);

	Fraction c = a.mult(b);
	c.print();

	// c = a.add(b);
	// c = a.operator+(b);
	c = a + b;

	c.print();

	cout << c << endl;

	deque<Fraction> myQueue;

	myQueue.push_back(a);
	myQueue.push_back(b);
	myQueue.push_front(c);

	for(int i=0; i<3; i++)
	{
		cout << myQueue.front() << endl;
		myQueue.pop_front();
	}

	_getch();
	return 0;
}