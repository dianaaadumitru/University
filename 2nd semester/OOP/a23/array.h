#pragma once
#include <stdlib.h>
#include "holiday.h"

typedef void* TElem;

typedef struct
{
    TElem* elements;
    int size, capacity;
}Dynamic_array;

Dynamic_array* create_array(int capacity);

void destroy_array(Dynamic_array* dynamic_array);

Dynamic_array * copyArray(Dynamic_array * dynamic_array);

int size_of_array(Dynamic_array* dynamic_array);

void resize_array(Dynamic_array * dynamic_array);

int almostFullCapacityUsed(Dynamic_array * dynamicVector);

void appendDynamicallyVector(Dynamic_array * dynamicVector, TElem elementToAppend);

int deleteByPosition(Dynamic_array * dynamicVector, int positionToDelete);

int updateByPosition(Dynamic_array * dynamicVector, int positionToUpdate, TElem elementToUpdateWith);

TElem getElementByPosition(Dynamic_array * dynamicVector, int positionToFind);
