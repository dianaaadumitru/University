#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b)
///BC=WC=TC: Theta(1)
{
    this->currentElement = b.sortedBag.head;
    this->currentAmount = 0;
}

TComp SortedBagIterator::getCurrent()
///BC=WC=TC: Theta(1)
{
    if (!this->valid())
        throw exception();
    return this->bag.sortedBag.nodes[this->currentElement].info;
}

bool SortedBagIterator::valid()
///BC=WC=TC: Theta(1)
{
	return this->currentElement != -1;
}

void SortedBagIterator::next()
///BC=WC=TC: Theta(1)
{
    if (!this->valid())
        throw exception();
    if (this->currentAmount < this->bag.sortedBag.nodes[this->currentElement].freq - 1)
        this->currentAmount++;
    else{
        this->currentAmount = 0;
        this->currentElement = this->bag.sortedBag.nodes[this->currentElement].next;
    }
}

void SortedBagIterator::first()
///BC=WC=TC: Theta(1)
{
	this->currentElement = this->bag.sortedBag.head;
    this->currentAmount = 0;
}

