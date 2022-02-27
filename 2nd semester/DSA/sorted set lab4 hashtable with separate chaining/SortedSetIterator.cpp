#include "SortedSetIterator.h"
#include <exception>
#include <iostream>

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
///theta(m)
{
	currentVal = new Node* [m.m];
    for (int i = 0; i < multime.m; ++i) {
        currentVal[i] = multime.hashTable[i];
    }
    first();
}

void SortedSetIterator::first()
///theta(m)
{
    for (int i = 0; i < multime.m; ++i) {
        currentVal[i] = multime.hashTable[i];
    }
    Node* first = nullptr;
    int pos;
    for (int i = 0; i < multime.m; ++i) {
        if (currentVal[i] == nullptr)
            continue;
        if (first == nullptr || multime.relation(currentVal[i]->elem, first->elem)){
            first = currentVal[i];
            pos = i;
        }
    }

    if (first != nullptr){
        currentElement = first->elem;
        currentVal[pos] = currentVal[pos]->next;
    } else{
        currentElement = NULL_TELEM;
    }
}

void SortedSetIterator::next()
///theta(m)
{
    if (!valid())
        throw exception();
    Node* next = nullptr;
    int pos;
    for (int i = 0; i < multime.m; ++i) {
        if (currentVal[i] == nullptr)
            continue;
        if (next == nullptr || multime.relation(currentVal[i]->elem, next->elem)){
            next = currentVal[i];
            pos = i;
        }
    }
    if (next != nullptr){
        currentElement = next->elem;
        currentVal[pos] = currentVal[pos]->next;
    } else {
        currentElement = NULL_TELEM;
    }
}

TElem SortedSetIterator::getCurrent()
///theta(1)
{
    if (!valid())
        throw exception();
    return currentElement;
}

bool SortedSetIterator::valid() const
///theta(1)
{
    return currentElement != NULL_TELEM;
}


