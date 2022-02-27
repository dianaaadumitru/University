
#include <iostream>
#include <assert.h>
#include "Matrix.h"
#include "ExtendedTest.h"
#include "ShortTest.h"

using namespace std;

void testNumberOfNonZeroElems()
{
    cout<<"Test new functionality\n";
    Matrix m(6, 7);
    m.modify(1, 2, 11);
    m.modify(1, 3, 11);
    assert(m.numberOfNonZeroElems(0) == 0);
    assert(m.numberOfNonZeroElems(2) == 0);
    assert(m.numberOfNonZeroElems(1) == 2);
    m.modify(4, 1, 3);
    m.modify(4,6,11);
    m.modify(2,3,1);
    m.modify(4, 0, 22);
    assert(m.numberOfNonZeroElems(4) == 3);
    try {
        m.numberOfNonZeroElems(-1);
        assert(false);
    }
    catch (exception&) {
        assert(true);
    }

    try {
        m.numberOfNonZeroElems(11);
        assert(false);
    }
    catch (exception&) {
        assert(true);
    }
}

int main() {
	testAll();
	testNumberOfNonZeroElems();
	testAllExtended();

	cout << "Test End" << endl;
	system("pause");
}