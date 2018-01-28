#include <iostream>
#include <conio.h>
#include <list>


using namespace std;

template <typename T>
class Node {
public:
	T data;
	Node * forward;
	Node * backward;
};

template <typename T>
class DoublyLinkedList {
public:
	DoublyLinkedList() {
		first = NULL;
		last = NULL;
		count = 0;
	}
	~DoublyLinkedList();
	void insertFirst(const T& data);
	void insertLast(const T& data);
	void deleteAt(const T& index);
	void deleteFirst();

private:
	Node<T> * first;
	Node<T> * last;
	int count;
};

template <typename T>
void DoublyLinkedList<T>::insertLast(const T& data) {
	Node<T> * temp = new Node<T>;
	temp->data = data;
	temp->forward = NULL;
	temp->backward = last;

	//Empty list
	if (first == NULL) {
		first = temp;
	}
	else {
		last->forward = temp;
	}
	last = temp;
	count++;

}

template <typename T>
void DoublyLinkedList<T>::insertFirst(const T& data) {

	Node<T> * temp = new Node<T>;
	temp->data = data;
	temp->forward = first;
	temp->backward= NULL;

	if (first == NULL) {
		//empty list scenario
		last = temp;
	}
	else{
		first->backward = temp;
	}
	first = temp;
	count++;
}


template <typename T>
void DoublyLinkedList<T>::deleteAt(const T& index){
	
	if(count == 0){
		cerr << "The list was already empty" << endl;
	}
	
	if(index >= count){
		cerr << "The index was too big" << endl;
	}

	if(index == 0){
		deleteFirst();
		return;
	}

	if (index == count - 1){
		deleteLast();
		return;
	}

	


}

template <typename T>
void DoublyLinkedList<T>::deleteFirst() {
	if (first == NULL) {
		//nothing to delete, just get out
		return;
	}

	Node<T> * temp = first; 
	first = first->forward;
	if (first == NULL) {
		last = NULL;
	}
	else{
		first->backward = NULL;
	}
	delete temp;
	temp = NULL;
	count--;
}

template <typename T>
void DoublyLinkedList<T>::deleteLast() {

	if (first == NULL) {
		//empty list scenario
		return;
	}

	if (first == last) {
		//one node scenario
		delete first;
		first = NULL;
		last = NULL;
		count--;
		return;
	}

	Node<T> * temp = last;
	Node<T> * prev = first;

	while (prev->link != last) {
		prev = prev->link;
	}
	last = prev;
	last->link = NULL;

	delete temp;
	temp = NULL;
	
	count--;
}

template <typename T>
DoublyLinkedList<T>::~DoublyLinkedList() {
	Node<T> * temp = NULL;

	while (first != NULL) {
		temp = first->link;
		delete first;
		first = temp;
	}

	last = NULL;
	count = 0;
}

int main() {
	/*
	Node<int> *ptr = new Node<int>;
	ptr->data = 20;

	Node<int> *another = new Node<int>;
	another->data = 27;

	ptr->link = another;

	another = new Node<int>;
	another->data = 15;

	ptr->link->link = another;

	ptr->link->link->link = NULL;
	*/
	DoublyLinkedList<int> mine;
	mine.insertLast(20);
	mine.insertLast(27);
	mine.insertLast(15);
	mine.insertLast(42);
	mine.insertLast(30);

	mine.deleteAt(2);// should remove 15
	mine.deleteAt(3);// should remove 30
	mine.deleteAt(0);// should remove 20
	mine.deleteAt(0);// should remove 27
	mine.deleteAt(0);// should remove 42
	mine.deleteAt(0); // error check



	_getch();
	return 0;
}