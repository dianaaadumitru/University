#include "Matrix.h"
#include <exception>
#include <iostream>
using namespace std;


Matrix::Matrix(int nrLines, int nrCols)
///BC: theta(1)
///WC: theta(1)
///TC: theta(1)
///creates a new matrix with a given number of lines and columns
///pre: nrLines belong to N* and nrCols belongs to N*
///post: it creates a matrix with nrLines lines and nrCols columns
///throws: an exception if nrLines or nrCols is <= 0
{
    if(nrLines <=0 or nrCols <= 0)
        throw exception();
    this->lines = nrLines;
    this->cols = nrCols;
    this->elements = new MatrixElement[nrLines*nrCols];
    this->size = 0;
}


int Matrix::nrLines() const
///BC: theta(1)
///WC: theta(1)
///TC: theta(1)
///returns the number of lines
{
    return this->lines;
}

int Matrix::nrColumns() const
///BC: theta(1)
///WC: theta(1)
///TC: theta(1)
///returns the number of columns
{
    return this->cols;
}


int Matrix::find_pos_value(int i, int j) const
///BC: theta(1) (the value is on the first position)
///WC: theta(size)(the value is located on the last position)
///TC: O(size)
///returns the index of the value from pos (i, j)
{
    for (int l = 0; l < this->size; ++l)
        if (this->elements[l].line == i && this->elements[l].column == j)
        {
            return l;
        }
    return -1;
}

TElem Matrix::element(int i, int j)
///BC: theta(1) (the value is on the first position)
///WC: theta(size)(the value is located on the last position)
///TC: O(size)
///returns the element from line i and column j (indexing starts from 0)
///pre: 0 <= i < nrLines, 0 <= j < nrCols
///throws exception if (i,j) is not a valid position in the Matrix
{
    if (i < 0 or i > this->nrLines() - 1 or j < 0 or j > this->nrColumns() - 1)
        throw exception();
    int nr;
    nr = find_pos_value(i, j);
    if (nr != -1)
        return this->elements[nr].value;

    return NULL_TELEM;
}

TElem Matrix::modify(int i, int j, TElem e)
///BC: theta(1): - the size of the array is 0
///              - e = 0 and the current value != 0
///              - e != 0 and the current value != 0
///              - e = 0 and the current_value != 0 and the current_value is the last element of the matrix array
///              - e != 0 and the current_value = 0 and and e should be inserted on the last position of the matrix array
///WC: theta(size): - e = 0 and the current_value != 0 and the current_value is the first element of the matrix array
///              - e != 0 and the current_value = 0 and and e should be inserted on the first position of the matrix array
///TC: O(size)
///modifies the value from line i and column j (assume 0-based indexing)
///pre: 0 <= i < nrLines, 0 <= j < nrCols, e belongs to TElem
///post: the value from position (i,j) is set to e; returns the previous value from the position
///throws: an exception if (i,j) is not a valid position in the Matrix
{
    if (i < 0 or i > this->nrLines() - 1 or j < 0 or j > this->nrColumns() - 1)
        throw exception();
    if(this->size == 0) //there is no element in the list
    {
        this->elements[0].value = e;
        this->elements[this->size].column = j;
        this->elements[this->size++].line = i;
        return NULL_TELEM;
    }
    else
    {
        if (find_pos_value(i,j) == -1)  //there was no element on that position in the list
        {
            if (e != 0)  //if e = 0 we do nothing otherwise the element is inserted on the right position
            {
                insert_in_matrix(i,j,e);
            }
            return NULL_TELEM;
        }
        else    //the element already exists in the list
        {
            TElem old = this->elements[find_pos_value(i,j)].value;
            if (e != 0)     //if e != 0 we modify the value of the element
            {
                this->elements[find_pos_value(i,j)].value = e;
            }
            else    //if e = 0, the element is removed from the list
            {
                remove_from_matrix(i, j);
            }
            return old;
        }
    }
}

int Matrix::find_pos_to_insert(int i, int j) const
///BC: theta(1)
///WC: theta(size)
///TC: O(size)
///returns the index where a new element should be added in order to have an ordered lexicographically array considering the <line, column>
///pre: 0 <= i < nrLines, 0 <= j < nrCols
///throws exception if (i,j) is not a valid position in the Matrix
{
    if (i < 0 or i > this->nrLines() - 1 or j < 0 or j > this->nrColumns() - 1)
        throw exception();
    if (this->elements[0].line > i || (this->elements[0].line == i && this->elements[0].column > j))
        return 0;

    if (this->elements[this->size - 1].line < i || (this->elements[this->size - 1].line == i) && this->elements[this->size - 1].column < j)
        return this->size;

    for (int k = 0; k < this->size - 1; k++) {
        if (this->elements[k].line == i && j < this->elements[k].column)
            return k;
        else if (this->elements[k].line == i && j < this->elements[k].column)
            return k+1;
        else if (this->elements[k].line > i && this->elements[k + 1].line < i)
            return k+1;
    }
}

void Matrix::insert_in_matrix(int i, int j, TElem value)
///BC: theta(1) - insert the element at the end
///WC: theta(size)
///TC: O(size)
///inserts a new element in the array of the matrix
///pre: 0 <= i < nrLines, 0 <= j < nrCols
{
    int pos = this->find_pos_to_insert(i, j);
    this->size++;
    for(int i = this->size - 1; i >=pos; i--)
    {
        this->elements[i].value = this->elements[i - 1].value;
        this->elements[i].line = this->elements[i - 1].line;
        this->elements[i].column = this->elements[i - 1].column;
    }
    this->elements[pos].value = value;
    this->elements[pos].line = i;
    this->elements[pos].column = j;
}

void Matrix::remove_from_matrix(int i, int j)
///BC: theta(1) - element is on the last position
///WC: theta(size)
///TC: O(size)
///removes an element from the array of the matrix
///pre: 0 <= i < nrLines, 0 <= j < nrCols
{
    int pos = this->find_pos_value(i, j);
    this->size--;
    for (int i = pos; i < this->size; i++)
    {
        this->elements[i].value = this->elements[i + 1].value;
        this->elements[i].line = this->elements[i + 1].line;
        this->elements[i].column = this->elements[i + 1].column;
    }
}

int Matrix::numberOfNonZeroElems(int line) const
///BC: theta(1)     - line < first element from the list
///                 - line > the last element from the list
///WC: theta(size)  - line  is find at the end of the list
///TC: O(size)
///returns the number of non-zero elements from a given line
///throws an exception if line is not valid
///pre: 0 <= i < nrLines
{
    if (line < 0 or line > this->nrLines() - 1)
        throw exception();

    if (line < this->elements[0].line)
        return 0;

    if(line > this->elements[this->size - 1].line)
        return 0;

    int ok = 0, nr_elems = 0;
    for (int i = 0; i < this->size && ok == 0; ++i) {
        if (this->elements[i].line > line)
            ok = 1;
        else if (this->elements[i].line == line)
            nr_elems++;
    }
    return nr_elems;
}



