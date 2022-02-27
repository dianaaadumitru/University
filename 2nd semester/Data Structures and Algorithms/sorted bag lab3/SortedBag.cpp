#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <exception>
#include <iostream>

SortedBag::SortedBag(Relation r)
///BC=WC=TC: Theta(n), n = number of keys
{
    this->relation = r;
    this->sortedBag.capacity = 10;
    this->sortedBag.nodes = new DLLANode[10];
    this->sortedBag.head = -1;
    for (int i = 0; i < this->sortedBag.capacity - 1; ++i) {
        this->sortedBag.nodes[i].next = i + 1;
        this->sortedBag.nodes[i + 1].prev = i;
    }
    this->sortedBag.nodes[this->sortedBag.capacity - 1].next = -1;
    this->sortedBag.nodes[0].prev = -1;

    this->sortedBag.firstEmpty = 0;
    this->sortedBag.size = 0;
    this->sortedBag.actualSize = 0;
}

int SortedBag::searchPozElem(TComp e) const
///BC: theta(1) - search for the first element
///WC: theta(n), n = number of keys
///TC: O(n), n = number of keys
{
    int current = this->sortedBag.head;
    while (current != -1 && this->sortedBag.nodes[current].info != e)
    {
        current = this->sortedBag.nodes[current].next;
    }
    return current;
}

int SortedBag::allocate()
///BC=WC=TC: Theta(1)
{
    int newElem = this->sortedBag.firstEmpty;
    if (newElem != -1){
        this->sortedBag.firstEmpty = this->sortedBag.nodes[this->sortedBag.firstEmpty].next;
        if (this->sortedBag.firstEmpty != -1){
            this->sortedBag.nodes[this->sortedBag.firstEmpty].prev = -1;
        }
        this->sortedBag.nodes[newElem].next = -1;
        this->sortedBag.nodes[newElem].prev = -1;
    }
    return newElem;
}

void SortedBag::free(int poz)
///BC=WC=TC: Theta(1)
{
    this->sortedBag.nodes[poz].next = this->sortedBag.firstEmpty;
    this->sortedBag.nodes[poz].prev = -1;

    if (this->sortedBag.firstEmpty != -1){
        this->sortedBag.nodes[this->sortedBag.firstEmpty].prev = poz;
    }
    this->sortedBag.firstEmpty = poz;
}

void SortedBag::resize()
///BC=WC=TC: Theta(n), n = capacity
{
    auto newNodes = new DLLANode[this->sortedBag.capacity * 2];
    for (int i = 0; i < this->sortedBag.capacity; ++i) {
        newNodes[i] = this->sortedBag.nodes[i];
    }

    // init the second half nexts
    for (int i = this->sortedBag.capacity; i < 2 * this->sortedBag.capacity - 1; ++i) {
        newNodes[i].next = i + 1;
    }
    newNodes[this->sortedBag.capacity * 2 - 1].next = -1;

    // init the second half prevs
    for (int i = this->sortedBag.capacity + 1; i < 2 * this->sortedBag.capacity; ++i){
        newNodes[i].prev = i - 1;
    }
    newNodes[this->sortedBag.capacity].prev = -1;

    this->sortedBag.firstEmpty = this->sortedBag.capacity;
    this->sortedBag.capacity *= 2;
    delete [] this->sortedBag.nodes;
    this->sortedBag.nodes = newNodes;
}

void SortedBag::insertPosition(TElem e, int poz)
///BC: Theta(1) - insert at the first position
///WC: Theta(n), n = number of keys
///TC: O(n), n = number of keys
{
    int newElem = this->allocate();
    if (newElem == -1)
    {
        this->resize();
        newElem = this->allocate();
    }

    this->sortedBag.nodes[newElem].info = e;

    if (poz == 0)
    {
        if (this->sortedBag.head == -1)
        {
            this->sortedBag.head = newElem;
            this->sortedBag.tail = newElem;
        }
        else
        {
            this->sortedBag.nodes[newElem].next = this->sortedBag.head;
            this->sortedBag.nodes[this->sortedBag.head].prev = newElem;
            this->sortedBag.head = newElem;
        }
    }
    else
    {
        int nodC = this->sortedBag.head;
        int pozC = 0;
        while (nodC != -1 && pozC < poz - 1)
        {
            nodC = this->sortedBag.nodes[nodC].next;
            pozC = pozC + 1;
        }
        int nodNext;
        if (nodC != -1)
        {
            nodNext = this->sortedBag.nodes[nodC].next;
            this->sortedBag.nodes[newElem].next = nodNext;
            this->sortedBag.nodes[newElem].prev = nodC;
            this->sortedBag.nodes[nodC].next = newElem;
            if (nodNext == -1)
            {
                this->sortedBag.tail = newElem;
            }
            else
            {
                this->sortedBag.nodes[nodNext].prev = newElem;
            }
        }
        else
            throw std::exception();
    }
}

void SortedBag::add(TComp e)
///BC: Theta(1) -if searchPozElem has the complexity Theta(1)
///WC: Theta(n), n = number of keys
///TC: O(n), n = number of keys
{
	int poz = this->searchPozElem(e);
    this->sortedBag.size++;
	if(poz == -1)
    {
        poz = this->sortedBag.head;

	    if (poz != -1){
	        int index = 0;
            while (poz != -1 && this->relation(this->sortedBag.nodes[poz].info, e)){
                poz = this->sortedBag.nodes[poz].next;
                index++;
            }
            if (poz == -1) {

                this->insertPosition(e, this->sortedBag.actualSize);
                this->sortedBag.nodes[this->sortedBag.actualSize].freq = 1;
            } else {
                this->insertPosition(e, index);
                this->sortedBag.nodes[searchPozElem(e)].freq = 1;
            }
	    } else {
	        poz = 0;
            this->insertPosition(e, poz);
            this->sortedBag.nodes[poz].freq = 1;
	    }
        this->sortedBag.actualSize++;

    } else
        this->sortedBag.nodes[poz].freq ++;
}


bool SortedBag::remove(TComp e)
///BC: Theta(1) -if we want to remove an element that has the frequency > 1
///WC: Theta(n), n = number of keys
///TC: O(n), n = number of keys
{
	int pos = this->searchPozElem(e);
    if (pos == -1)
	    return false;

    if (this->sortedBag.nodes[pos].freq == 1){
        int nodC = this->sortedBag.head;
        int prevNode = -1;

        while (nodC != -1 && this->sortedBag.nodes[nodC].info != e){
            prevNode = nodC;
            nodC = this->sortedBag.nodes[nodC].next;
        }

        if (nodC != -1){
            this->sortedBag.size--;
            this->sortedBag.actualSize--;
            if (nodC == this->sortedBag.head)
                this->sortedBag.head = this->sortedBag.nodes[this->sortedBag.head].next;
            else
                this->sortedBag.nodes[prevNode].next = this->sortedBag.nodes[nodC].next;
            this->free(nodC);
        }
    } else {
        this->sortedBag.size--;
        this->sortedBag.nodes[pos].freq--;
    }
    return true;
}


bool SortedBag::search(TComp elem) const
///BC: Theta(1) -if the element we search for is the first elem
///WC: Theta(n), n = number of keys
///TC: O(n), n = number of keys
{
	int current = this->sortedBag.head;

    while (current != -1 && this->sortedBag.nodes[current].info != elem){
        current = this->sortedBag.nodes[current].next;
    }
    if (current != -1)
        return true;
    return false;
}

int SortedBag::nrOccurrences(TComp elem) const
///BC: Theta(1) -if the element we search for is the first elem
///WC: Theta(n), n = number of keys
///TC: O(n), n = number of keys
{
	int current = this->sortedBag.head;
    while (current != -1 && this->sortedBag.nodes[current].info != elem){
        current = this->sortedBag.nodes[current].next;
    }
    if (current == -1)
	    return 0;
    return this->sortedBag.nodes[current].freq;
}



int SortedBag::size() const
///BC=WC=TC: Theta(1)
{
	return this->sortedBag.size;
}

bool SortedBag::isEmpty() const
///BC=WC=TC: Theta(1)
{
	return this->sortedBag.size == 0;
}

SortedBagIterator SortedBag::iterator() const
///BC=WC=TC: Theta(1)
{
	return SortedBagIterator(*this);
}

SortedBag::~SortedBag()
///BC=WC=TC: Theta(1)
{
	delete [] this->sortedBag.nodes;
}

//void SortedBag::printBag() const
//{
//    std::cout << std::endl;
//
//    int current = this->sortedBag.head;
//    while (current != -1)
//    {
//        std::cout << this->sortedBag.nodes[current].info << " " <<this->sortedBag.nodes[current].freq << std::endl;
//        current = this->sortedBag.nodes[current].next;
//    }
//    std::cout << std::endl;
//}
