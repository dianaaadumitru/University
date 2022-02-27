#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>
#include <iostream>

using namespace std;

SMIterator::SMIterator(const SortedMap& m) : map(m)
///BC=WC=TC: theta(n)
{
	int node = m.bst.root;
    while (node != NULL_TVALUE){
        this->stack.push(node);
        node = m.bst.nodes[node].left;
    }
    if (!this->stack.empty()){
        this->currentNode = stack.top();
    } else {
        this->currentNode = NULL_TVALUE;
    }
}

void SMIterator::first()
///BC=WC=TC: theta(n)
{
    while (!this->stack.empty())
        this->stack.pop();
    int node = map.bst.root;
    while (node != NULL_TVALUE){
        this->stack.push(node);
        node = map.bst.nodes[node].left;
    }
    if (!this->stack.empty()){
        this->currentNode = stack.top();
    } else {
        this->currentNode = NULL_TVALUE;
    }
}

void SMIterator::next()

{
    if (!valid())
        throw exception();
    int node = this->stack.top();
    this->stack.pop();
    if (map.bst.nodes[node].right !=NULL_TVALUE){
        node = map.bst.nodes[node].right;
        while (node != NULL_TVALUE){
            this->stack.push(node);
            node = map.bst.nodes[node].left;
        }
    }
    if (!this->stack.empty()) {
        this->currentNode = this->stack.top();
    }
    else {
        this->currentNode = NULL_TVALUE;
    }
}

bool SMIterator::valid() const{
	//TODO - Implementation
    if (map.isEmpty())
        return false;
	return this->currentNode != NULL_TVALUE;
}

TElem SMIterator::getCurrent() const{
	//TODO - Implementation
    if (!this->valid())
        throw std::exception();
	return map.bst.nodes[this->currentNode].info;
}

void SMIterator::print() const {
    std::stack<int> aux;
    int current = map.bst.root;
    if (current != NULL_TVALUE){
        while (current != NULL_TVALUE || !aux.empty()){
            while (current != NULL_TVALUE){
                aux.push(current);
                current = map.bst.nodes[current].left;
            }
            current = aux.top();
            aux.pop();
            std::cout<<map.bst.nodes[current].info.first<<" "<<map.bst.nodes[current].info.second<<"\n";
            current = map.bst.nodes[current].right;
        }
    }
}


