#include <iostream>
#include <conio.h>
#include <vector>
#include <list>
#include <algorithm>

using namespace std;

int main(){
	vector<int> v;

	v.push_back(64);
	v.push_back(12);
	v.push_back(32);
	v.push_back(49);
	v.push_back(14);
	v.push_back(42);

	for (unsigned int i=0; i<v.size(); i++){
		cout << v.at(i) << endl;
	}

	cout << "-------------" << endl;

	for (vector <int>::iterator iter = v.begin(); iter != v.end(); ++ iter){
		cout << *iter << endl;
	}

	cout << "-------------" << endl;

	reverse(v.begin(), v.end());

	for(auto e : v)
	{
		cout << e << endl;
	}

	cout << "-------------" << endl;

	list<int> l;
	l.push_back(64);
	l.push_back(12);
	l.push_back(32);
	l.push_back(49);
	l.push_back(14);
	l.push_back(42);

	for (unsigned int i=0; i<l.size(); i++){
		int temp = l.front();
		l.pop_front();
		cout << temp << endl;
		l.push_back(temp);
	}
	cout << "-------------" << endl;

	for(list<int>::iterator iter = l.begin(); iter != l.end(); ++iter){
		cout << *iter << endl;
	}

	cout << "-------------" << endl;

	reverse(l.begin(), l.end());

	for(auto e : l)
	{
		cout << e << endl;
	}


	_getch();
	return 0;
}