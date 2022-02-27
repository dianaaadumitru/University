#pragma once
#include "SortedMap.h"
#include <stack>

//DO NOT CHANGE THIS PART
class SMIterator{
	friend class SortedMap;
private:
	const SortedMap& map;
	int currentNode;
	std::stack<int> stack;
	SMIterator(const SortedMap& mapionar);

	//TODO - Representation

public:
    void print() const;
	void first();
	void next();
	bool valid() const;
    TElem getCurrent() const;
};

