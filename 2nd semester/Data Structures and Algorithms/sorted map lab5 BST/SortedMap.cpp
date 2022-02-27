#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>
#include <iostream>
#include <iomanip>

using namespace std;

SortedMap::SortedMap(Relation r)
///BC=WC=TC: theta(n)
{
    this->r = r;
    this->bst.capacity = 10;
    this->bst.size = 0;
    this->bst.root = 0;
    this->bst.firstEmpty = 0;
    this->bst.nodes = new BSTNode[this->bst.capacity];
    for (int i = 0; i < this->bst.capacity; ++i) {
        this->bst.nodes[i].info = NULL_TPAIR;
        this->bst.nodes[i].left = NULL_TVALUE;
        this->bst.nodes[i].right = NULL_TVALUE;
    }
}

TValue SortedMap::add(TKey k, TValue v)
///BC: theta(1): - add an element to an empty list
///              - change the value of the first key, search has complexity 1
///WC: theta(n): resize, insert, search has complexity n
///TC: O(n)
{
    if (this->isEmpty()){
        this->bst.nodes[0].info = std::pair<TKey, TValue>(k, v);
        this->bst.firstEmpty = 1;
        this->bst.size++;
        return NULL_TVALUE;
    }
    int old = this->search(k);
    if (old) {
        int current = this->bst.root;
        while (current != NULL_TVALUE) {
            if (this->bst.nodes[current].info.second == old) {
                this->recomputeFirstEmpty();
                this->bst.nodes[current].info.second = v;
                return old;
            } else if (this->r(bst.nodes[current].info.first, k)) {
                current = this->bst.nodes[current].right;
            } else {
                current = this->bst.nodes[current].left;
            }
        }
    }

    if (this->bst.size == this->bst.capacity - 1)
        this->resize();

    this->insert(this->bst.root, std::pair<TKey, TValue>(k, v));
    this->bst.size++;
    return NULL_TVALUE;
}

TValue SortedMap::search(TKey k) const
///BC: theta(1) - looking for the first element
///WC: theta(n) - go through all elements
///TC: O(n)
{
	int current = this->bst.root;
    while (current != NULL_TVALUE){
        if (this->bst.nodes[current].info.first == k){
            return bst.nodes[current].info.second;
        } else if (this->r(bst.nodes[current].info.first, k)){
            current = this->bst.nodes[current].right;
        } else {
            current = this->bst.nodes[current].left;
        }
    }
	return NULL_TVALUE;
}

TValue SortedMap::remove(TKey k)
///BC=WC=TC: theta(height_of_subtree) <==>theta(n)
{
    bool found = false;
    int old = this->search(k);
    if (old != NULL_TVALUE){
        int currentIndex = this->bst.root;
        while (currentIndex != NULL_TVALUE){
            if (this->bst.nodes[currentIndex].info.first == k){
                this->bst.root = this->removeRec(this->bst.root, k, found);
                this->bst.size--;
                this->bst.firstEmpty = currentIndex;
                return old;
            }

            if (this->r(this->bst.nodes[currentIndex].info.first, k))
                currentIndex = this->bst.nodes[currentIndex].right;
            else
                currentIndex = this->bst.nodes[currentIndex].left;
        }
    }
    return NULL_TVALUE;
}

int SortedMap::size() const
///theta(1)
{
	return this->bst.size;
}

bool SortedMap::isEmpty() const
///theta(1)
{
	return this->bst.size == 0;
}

SMIterator SortedMap::iterator() const
///theta(1)
{
	return SMIterator(*this);
}

SortedMap::~SortedMap()
///BC=WC=TC: theta(1)
{
	delete[] this->bst.nodes;
}

void SortedMap::resize()
///BC=WC=TC: theta(capacity)
{
    BSTNode *aux = new BSTNode[this->bst.capacity * 2];
    for (int i = 0; i < this->bst.capacity; i++) {
        aux[i] = this->bst.nodes[i];
    }

    for (int i = this->bst.capacity; i < this->bst.capacity * 2; ++i) {
        aux[i].info = NULL_TPAIR;
        aux[i].left = NULL_TVALUE;
        aux[i].right = NULL_TVALUE;
    }
    recomputeFirstEmpty();
    this->bst.capacity *= 2;
    delete[] this->bst.nodes;
    this->bst.nodes = aux;
}

int SortedMap::insert(int node, TElem elem)
///BC=WC=TC: theta(height_of_subtree) <==>theta(n)
{
    if (node == NULL_TVALUE){
        this->bst.nodes[this->bst.firstEmpty].info = elem;
        int old = this->bst.firstEmpty;
        this->recomputeFirstEmpty();
        return old;
    } else if (!this->r(this->bst.nodes[node].info.first, elem.first)){
        this->bst.nodes[node].left = insert(this->bst.nodes[node].left, elem);
        return node;
    } else {
        this->bst.nodes[node].right = insert(this->bst.nodes[node].right, elem);
        return node;
    }
}

void SortedMap::recomputeFirstEmpty()
///BC=WC=TC: theta(n)
{
    while (this->bst.nodes[this->bst.firstEmpty].info != NULL_TPAIR) {
        this->bst.firstEmpty++;
    }
}

int SortedMap::removeRec(int node, TKey k, bool &found)
///BC=WC=TC: theta(height_of_subtree) <==>theta(n)
{
    if (node == NULL_TVALUE)
        return node;

    if (this->bst.nodes[node].info.first == k){
        found = true;
        if (this->bst.nodes[node].left == NULL_TVALUE && this->bst.nodes[node].right == NULL_TVALUE){
            return NULL_TVALUE;
        } else if (this->bst.nodes[node].left == NULL_TVALUE) {
            int rightIndex = this->bst.nodes[node].right;
            this->bst.nodes[node].right = NULL_TVALUE;
            this->bst.nodes[node].left = NULL_TVALUE;
            this->bst.nodes[node].info = NULL_TPAIR;
            return rightIndex;

        } else if (this->bst.nodes[node].right == NULL_TVALUE){
            int leftIndex = this->bst.nodes[node].left;
            this->bst.nodes[node].right = NULL_TVALUE;
            this->bst.nodes[node].left = NULL_TVALUE;
            this->bst.nodes[node].info = NULL_TPAIR;
            return leftIndex;

        }
        int min = this->minimum(this->bst.nodes[node].right);
        this->bst.nodes[node].info = this->bst.nodes[min].info;
        this->bst.nodes[node].right = removeRec(this->bst.nodes[node].right, this->bst.nodes[min].info.first, found);

    } else if (!this->r(this->bst.nodes[node].info.first, k)){
        this->bst.nodes[node].left = removeRec(this->bst.nodes[node].left, k, found);
    } else {
        this->bst.nodes[node].right = removeRec(this->bst.nodes[node].right, k, found);

    }

    return node;
}

int SortedMap::minimum(int node)
///BC=WC=TC: theta(height_of_subtree) <==>theta(n)
{
    int current = node;
    while (this->bst.nodes[current].left != NULL_TVALUE){
        current = this->bst.nodes[current].left;
    }

    return current;
}
