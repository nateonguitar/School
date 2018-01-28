#include <iostream>
#include <conio.h>

using namespace std;

//------------------------------//

template <typename T>
class Node{
public: T data;
	Node * link;
};

//------------------------------//

template <typename T>
class LinkedList{
public:
	LinkedList();
	~LinkedList();
	void addFirst(const T& data);
	void addLast(const T& data);
	void deleteFirst();
	void deleteLast();
private:
	Node<T> * first;
	Node<T> * last;
	unsigned int count;
};

template <typename T>
LinkedList<T>::LinkedList(){
	first = NULL;
	last = NULL;
	count = 0;
}

template <typename T>
LinkedList<T>::LinkedList(){
	Node<T> * current = NULL;
	while(first != NULL){
		current = first;
		first = first->link;
		delete current;
	}

	last = NULL;
	count = 0;
}

template <typename T>
void LinkedList<T>::addFirst(const T& data){
	Node<T> * temp = new Node<T>();
	temp->data = data;
	temp->link = first;
	first = temp;
	
	if(last == NULL){
		last = temp;
	}

	count++;
}

template <typename T>
void LinkedList<T>::addLast(const T& data){
	Node<T> * temp = new Node<T>();
	temp->data = data;
	temp->link = NULL;
	if(last == NULL)
	{
		first = temp;
	}
	else{
		last->link = temp;
	}

	last = temp;
	count++;
}

template<typename T>
void LinkedList<T>::deleteFirst(){
	if(first == NULL>{
		// zero node scenario
		return;
	}

	Node<T> * current = first->link;
	// current is pointing to the second node
	delete first;
	first = current;
	count--;
	if(first == NULL)
	{
		// one node scenario
		last = NULL;
	}
}

template <typename T>
void LinkedList<T>::deleteLast(){
	if(first == NULL){
		// 0 node scenario
		return;
	}
	if(first == last){
		// one node scenario
		delete last;
		first = NULL;
		last = NULL;
		count --;
		return;
	}

	Node<T> * current = first;
	while(current->link != last)
	{
		current = current->link;
	}
	// current is at the second to last node

	delete last;
	current->link = NULL;
	last = cur;
	count--;
}

//------------------------------//

int main(){

	LinkedList<int> myList;
	myList.addLast(42);
	myList.addLast(10);
	myList.addLast(56);

	/*
	Node<int> *temp = new Node<int>();

	temp->data = 42;
	temp->link = NULL;

	Node<int> *second = new Node<int>();
	second->data = 10;
	second->link = NULL;
	temp->link = second;

	Node<int> *third = new Node<int>();
	third->data = 56;
	third->link = NULL;
	second->link = third;
	// same thing temp->link->link = third;
	*/


	_getch();
	return 0;
}