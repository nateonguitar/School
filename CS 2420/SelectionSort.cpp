#include <iostream>
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include <conio.h>

using namespace std;

class Sort {
public:
	Sort() {
		arr = new int[1000];
		arraySize = 1000;
	}
	void randomize() {
		srand(time(NULL));
		for (int i = 0; i < arraySize; i++) {
			arr[i] = rand() % 1000;
		}
	}
	virtual void sort() = 0;
	void print() {
		for (int i = 0; i < arraySize; i++) {
			cout << arr[i] << " ";
		}
		cout << endl << endl;
	}
	~Sort() {
		delete[] arr;
	}
protected:
	int * arr;
	int arraySize;
	
};

class Selection : public Sort {
public:
	void sort() {
		for (int i = 0; i < arraySize - 1 ; i++) {
			int smallestIndex = 0;
			smallestIndex = i;
			for (int j = i+1; j < arraySize; j++) {
				if(arr[j] < arr[smallestIndex]){
					smallestIndex = j;
				}
			}
			// swap them
			int temp = arr[i];
			arr[i] = arr[smallestIndex];
			arr[smallestIndex] = temp;
		}
	}
};

class Bubble : public Sort {
public:
	void sort() {
		for (int i = 0; i < arraySize - 1 ; i++) {
			for (int j = 0; j < arraySize - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}
};

class Insertion : public Sort {
public:
	void sort() {
		for (int i = 1; i < arraySize; i++) {
			// while the guy behind it is bigger
			// we need to swap
			int j = i;
			while(j >= 0 && arr[j-1] > arr[j]){
				int temp = arr[j];
				arr[j] = arr[j-1];
				arr[j-1] = temp;
				j--;
			}
		}
	}
};

class Quick : public Sort{
public:
	void sort(){
		sort(0, arraySize);
	}

private:
	void sort(int leftIndex, int rightMax){
		unsigned int current = leftIndex + 1;
		unsigned int smallest = leftIndex + 1;
		int pivot = arr[leftIndex];

		while(current < rightMax){
			if(arr[current] < pivot){
				int temp = arr[current];
				arr[current] = arr[smallest];
				arr[smallest] = temp;
				smallest++;
			}
			current++;
		}

		int temp = arr[smallest-1];
		arr[smallest-1] = arr[leftIndex];
		arr[leftIndex] = temp;

		// repeat for each sublist
		if(smallest-1 - leftIndex > 1){
			sort(leftIndex, smallest-1);
		}
		if(rightMax - smallest > 1){
			sort(smallest, rightMax);
		}
	}
};

int main() {
	cout << "bubble sort:" << endl;

	Sort * obj = NULL;
	obj = new Bubble();
	obj->randomize();
	obj->print();
	obj->sort();
	obj->print();
	delete obj;
	
	cout << "selection sort:" << endl;
	obj = new Selection();
	obj->randomize();
	obj->print();
	obj->sort();
	obj->print();
	delete obj;
	
	cout << "insertion sort:" << endl;
	obj = new Insertion();
	obj->randomize();
	obj->print();
	obj->sort();
	obj->print();
	delete obj;

	cout << "quick sort:" << endl;
	obj = new Quick();
	obj->randomize();
	obj->print();
	obj->sort();
	obj->print();
	delete obj;
	_getch();
}