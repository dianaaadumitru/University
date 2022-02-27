#include <iostream>
#include "SortedSet.h"
#include "SortedSetIterator.h"

SortedSet::SortedSet(Relation r)
///theta(m)
{
    this->relation = r;
    this->m = 127;
    this->tableSize = 0;
    this->loadFactor = 0.7;
    this->hashTable = new Node*[this->m];

    for (int i = 0; i < this->m; ++i) {
        hashTable[i] = nullptr;
    }
}

void SortedSet::resize()
///
{
    int* allValues = new int[this->m];
    int count = 0;
    SortedSetIterator it(*this);
    it.first();
    while(it.valid()){
        allValues[count++] = it.getCurrent();
        it.next();
    }
    Node** oldHashTable = this->hashTable;

    int oldM = this->m;
    this->m *= 2;
    this->m++;

    this->hashTable = new Node*[this->m];
    for (int i = 0; i < this->m; ++i) {
        hashTable[i] = nullptr;
    }
    this->tableSize = 0;
    for (int i = 0; i < count; ++i) {
        add(allValues[i]);
    }

    for (int i = 0; i < oldM; ++i) {
        Node* node = oldHashTable[i];
        while (node != nullptr){
            Node* next = node->next;
            delete node;
            node = next;
        }
    }
    delete[] oldHashTable;
}

bool SortedSet::add(TComp elem)
///BC: theta(1) - the element is added on the first position on its SLL
///WC:
{
	//TODO - Implementation
    if (search(elem))
        return false;
    int pos = this->hash(elem);
    this->tableSize++;

    Node* newNode = new Node;
    newNode->elem = elem;

    if (this->hashTable[pos] == nullptr || !this->relation(this->hashTable[pos]->elem, elem)){
        newNode->next = this->hashTable[pos];
        hashTable[pos] = newNode;
    } else {
        Node* current = this->hashTable[pos];
        Node* prev;
        while (current != nullptr && this->relation(current->elem, elem)){
            prev = current;
            current = current->next;
        }
        newNode->next = prev->next;
        prev->next = newNode;
    }
    if (this->getLoadedFactor() > this->loadFactor){
        this->resize();
    }
    return true;
}

bool SortedSet::remove(TComp elem)
///BC: theta(1): there is no element int the hash table, on that position
///              delete the first element in the SLL from that pos

///WC: theta(1+loadFactor)
///TC: O(1+loadFactor
{
    if (this->tableSize == 0)
        return false;
    int pos = this->hash(elem);
    Node* current = this->hashTable[pos];
    Node* prev = nullptr;
    if (this->hashTable[pos] == nullptr)
        return false;

    if (this->hashTable[pos]->elem == elem){
        Node* oldNode = hashTable[pos];
        this->hashTable[pos] = this->hashTable[pos]->next;
        delete oldNode;
        this->tableSize--;
        return true;
    }

    while (current != nullptr && this->relation(current->elem, elem)){
        if (current->elem == elem){
            Node *nextNode = current->next;
            delete nextNode;
            prev->next = nextNode;
            this->tableSize--;
            return true;
        }
        prev = current;
        current = current->next;
    }
    return false;
}

bool SortedSet::search(TComp elem) const
///BC: theta(1) - the element is on the firs position of the linked list
///WC: theta(1+loadFactor)
///TC: O(1+loadFactor)
{
    int pos = hash(elem);

    Node* current = hashTable[pos];
    while (current != nullptr && relation(current->elem, elem)){
        if (current->elem == elem){
            return true;
        }
        current = current->next;
    }
    return false;
}

int SortedSet::size() const
///theta(1)
{
	return this->tableSize;
}

bool SortedSet::isEmpty() const
///theta(1)
{
	return this->tableSize == 0;
}

SortedSetIterator SortedSet::iterator() const
///theta(1)
{
	return SortedSetIterator(*this);
}

SortedSet::~SortedSet()
///theta(m + number of elements)
{
    for (int i = 0; i < this->m; ++i) {
        Node* node = this->hashTable[i];
        while (node != nullptr){
            Node* next = node->next;
            delete node;
            node = next;
        }
    }
}

void SortedSet::print() {
    for (int i = 0; i < this->m; ++i) {
        std::cout<<i<<": ";
        Node* current = this->hashTable[i];
        while (current != nullptr) {
            std::cout<<current->elem<<" ";
            current = current->next;
        }
        std::cout<<"\n";
    }
}


