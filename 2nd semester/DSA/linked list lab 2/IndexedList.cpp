#include <exception>

#include "IndexedList.h"
#include "ListIterator.h"

using namespace std;


IndexedList::IndexedList()
/*
 * constructor
 * BC=WC=TC: theta(1)*/
{

    this->head = nullptr;
    this->tail = nullptr;
}

int IndexedList::size() const
/*
 * BC=WC=TC: theta(n), n = number of keys*/
{
    Node* currentNode = this->head;
    int size = 0;
    while (currentNode != nullptr)
    {
        size ++;
        currentNode = currentNode->next;
    }
    return size;
}


bool IndexedList::isEmpty() const
/*
 * BC=WC=TC: theta(1)
 */
{

    if (this->head == nullptr)
        return true;
	return false;
}

TElem IndexedList::getElement(int pos) const
/*
 * BC: theta(1) - if the element is on the first position
 * WC: theta(n), n - number of keys
 * TC: O(n), n - number of keys*/
{

    if (pos < 0 || pos > this->size())
        throw exception();

    Node * current;
    current = this->head;
    int index = 0;
    while (index != pos)
    {
        current = current->next;
        index++;
    }
	return current->info;
}

Node* IndexedList::getNodeFromPosition(int pos) const
/*
 * BC: theta(1) - if the element is on the first position
 * WC: theta(n), n - number of keys
 * TC: O(n), n - number of keys*/
{
    if (pos < 0 || pos > this->size())
        throw exception();

    Node * current;
    current = this->head;
    int index = 0;
    while (index != pos)
    {
        current = current->next;
        index++;
    }
    return current;
}

TElem IndexedList::setElement(int pos, TElem e)
/*
 * complexity depends on the function getNodeFromPosition
 *          - if getNodeFromPosition has the complexity theta(n) => setElement has the complexity theta(n)
 *          - if getNodeFromPosition has the complexity theta(n) => setElement has the complexity theta(n), n - number of keys
 *
 * so: BC: theta(1) - if the element is on the first position
 *    WC: theta(n), n - number of keys
 *    TC: O(n), n - number of keys*/
{
    if (pos < 0 || pos > this->size())
        throw exception();
	Node * current = this->getNodeFromPosition(pos);
	TElem old = current->info;
	current->info = e;
    return old;
}

void IndexedList::addToEnd(TElem e)
/*
 * BC=WC=TC: theta(1)
 */
{
    Node *new_node = new Node();
    new_node->info = e;

    if (this->head== nullptr) {
        this->head = new_node;
        this->tail =new_node;
    }
    else
    {
        this->tail->next = new_node;
        this->tail = tail->next;
    }

}

void IndexedList::addToPosition(int pos, TElem e)
/*
 * BC: theta(1)  - if we add to the end of the list
 *               - if we add to the beginning of the list
 * WC: theta(n), n - number of keys
 * TC:O(n), n - number of keys*/
{
    if (pos < 0 || pos > this->size())
    {
        throw exception();
    }

    if (pos == this->size())
    {
        this->addToEnd(e);
        return;
    }
    else
        if (pos == 0) //insert at the beginning
        {
            Node *new_node = new Node();
            new_node->info = e;
            new_node->next = this->head;
            this->head = new_node;
            return;
        }
    Node * current = new Node();
    current = this->head;
    int index = 1;
    while (index < pos)
    {
        index++;
        current = current->next;
    }
    Node *new_node = new Node();
    new_node->info = e;
    new_node->next = current->next;
    current->next = new_node;

}

TElem IndexedList::remove(int pos)
/*
 * BC: theta(1)  - if we remove the first element from the list
 *               - if we remove the last element from the list
 * WC: theta(n), n - number of keys
 * TC:O(n), n - number of keys*/
{
    if (pos < 0 || pos >= this->size())
        throw exception();

    if (pos == 0)
    {
        TElem old = this->head->info;
        if (this->size() == 1)
        {
            this->head = nullptr;
            this->tail = nullptr;
        }
        else
            this->head = this->head->next;
        return old;
    }
    else if (pos == this->size())
    {
        TElem old = this->tail->info;
        this->tail = nullptr;
        return old;
    }
    Node * current = new Node();
    current = this->head;
    Node * prev = new Node();
    int index = 0;
    while (current != nullptr && index < pos)
    {
        index++;
        prev = current;
        current = current->next;
    }
    TElem old = current->info;
    prev->next = current->next;
    delete current;
	return old;
}

int IndexedList::search(TElem e) const
/*
 * BC: theta(1)  - if the element we search for is on the first position
 * WC: theta(n), n - number of keys
 * TC:O(n), n - number of keys
 * */
{
    Node * current;
    current = this->head;
    int index = 0;
    while (current != nullptr && current->info != e)
    {
         current = current->next;
         index++;
    }
    if (current == nullptr)
        return -1;
	return index;
}

ListIterator IndexedList::iterator() const
/*
 *BC=WC=TC: theta(1)*/
{
    return ListIterator(*this);        
}

IndexedList::~IndexedList()
/*
 * BC=WC=TC: theta(n), n = number of keys*/
{
    while (this->head != nullptr)
    {
        Node * aux = new Node();
        aux = this->head;
        this->head = this->head->next;
        free(aux);
    }
}