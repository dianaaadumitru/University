#pragma once

//DO NOT CHANGE THIS PART
typedef int TElem;
#define NULL_TELEM 0

struct MatrixElement
{
    int line;
    int column;
    TElem value;
};

class Matrix {

private:
    MatrixElement *elements;
    int lines;
    int cols;
    int size;

    int find_pos_to_insert(int i, int j) const;
    void remove_from_matrix(int i, int j);
    int find_pos_value(int i, int j) const;
    void insert_in_matrix(int i, int j, TElem value);
public:
    //constructor
	Matrix(int nrLines, int nrCols);

	//returns the number of lines
	int nrLines() const;

	//returns the number of columns
	int nrColumns() const;

	//returns the element from line i and column j (indexing starts from 0)
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem element(int i, int j);

	//modifies the value from line i and column j
	//returns the previous value from the position
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem modify(int i, int j, TElem e);

    //returns the number of non-zero elements from a given line
    //throws an exception if line is not valid
    int numberOfNonZeroElems(int line) const;

};
