#pragma once
//DO NOT INCLUDE SETITERATOR

#include <cmath>

//DO NOT CHANGE THIS PART
typedef int TElem;
typedef TElem TComp;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TELEM -11111
class SortedSetIterator;

struct Node {
    TElem elem;
    Node *next;
};

class SortedSet {
	friend class SortedSetIterator;
private:
public:


    //TODO - Representation
    Node** hashTable;
    int m;
    ///theta(1)
    int hash(TElem elem) const { return abs(elem) % m; };
    int tableSize;
    Relation relation;
    double loadFactor;
    void resize();
    ///theta(1)
    double getLoadedFactor() const { return double(tableSize) / m; };

    //constructor
	SortedSet(Relation r);

	//adds an element to the sorted set
	//if the element was added, the operation returns true, otherwise (if the element was already in the set) 
	//it returns false
	bool add(TComp e);

	
	//removes an element from the sorted set
	//if the element was removed, it returns true, otherwise false
	bool remove(TComp e);

	//checks if an element is in the sorted set
	bool search(TElem elem) const;


	//returns the number of elements from the sorted set
	int size() const;

	//checks if the sorted set is empty
	bool isEmpty() const;

	//returns an iterator for the sorted set
	SortedSetIterator iterator() const;

	// destructor
	~SortedSet();

	void print();


};
