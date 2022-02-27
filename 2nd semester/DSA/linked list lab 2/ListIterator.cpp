#include "ListIterator.h"
#include "IndexedList.h"
#include <exception>

ListIterator::ListIterator(const IndexedList& list) : list(list)
/*BC=WC=TC: theta(1)*/
{
    this->current = this->list.head;
}

void ListIterator::first()
/*BC=WC=TC: theta(1)*/
{
    this->current = this->list.head;
}

void ListIterator::next()
/*BC=WC=TC: theta(1)*/
{
    if (this->current == NULL)
        throw std::exception();

    else this->current = this->current->next;
}

bool ListIterator::valid() const
/*BC=WC=TC: theta(1)*/
{
    return this->current != NULL;
}

TElem ListIterator::getCurrent() const
/*BC=WC=TC: theta(1)*/
{
    if (this->current == NULL)
        throw std::exception();

    TElem e = this->current->info;
    return e;
}