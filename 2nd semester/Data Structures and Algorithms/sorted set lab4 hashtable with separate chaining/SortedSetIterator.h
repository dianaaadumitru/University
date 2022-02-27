#pragma once
#include "SortedSet.h"

//DO NOT CHANGE THIS PART
class SortedSetIterator
{
	friend class SortedSet;
private:
	const SortedSet& multime;
	Node** currentVal;
	TElem currentElement = NULL_TELEM;
	SortedSetIterator(const SortedSet& m);

	//TODO - Representation

public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;
	void print();
};

