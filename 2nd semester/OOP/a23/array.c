#include "array.h"

Dynamic_array* create_array(int capacity)
{
    Dynamic_array* dynamic_array = (Dynamic_array*)malloc(sizeof(Dynamic_array));
    dynamic_array->capacity = capacity;
    dynamic_array->size = 0;
    dynamic_array->elements = (TElem*)malloc(sizeof(TElem)*capacity);

    return dynamic_array;
}

void destroy_array(Dynamic_array* dynamic_array)
{
    if(dynamic_array == NULL)
        return;
    free(dynamic_array->elements);
    dynamic_array->elements =NULL;
    free(dynamic_array);
}

Dynamic_array * copyArray(Dynamic_array * dynamic_array)
{
    Dynamic_array* new_array = create_array(dynamic_array->capacity);
    new_array->size = dynamic_array->size;

    for(int i = 0; i < new_array->size; i++)
        new_array->elements[i] = copy_holiday(dynamic_array->elements[i]);
    return new_array;
}

int size_of_array(Dynamic_array* dynamic_array)
{
    return dynamic_array->size;
}

void resize_array(Dynamic_array * dynamic_array)
{
    dynamic_array->capacity *= 2;
    TElem * new_elements = (TElem*) malloc(sizeof(TElem) * dynamic_array->capacity);
    for(int i = 0; i < dynamic_array->size; i++)
        new_elements[i] = copy_holiday(dynamic_array->elements[i]);
    free(dynamic_array->elements);
    dynamic_array->elements = new_elements;

}

void appendDynamicallyVector(Dynamic_array * dynamicVector, TElem elementToAppend)
{
    if (almostFullCapacityUsed(dynamicVector))
        resize_array(dynamicVector);
    dynamicVector->elements[dynamicVector->size] = elementToAppend;
    dynamicVector->size++;
}

int deleteByPosition(Dynamic_array * dynamicVector, int positionToDelete)
{
    if (positionToDelete < 0 || positionToDelete >= dynamicVector->size|| dynamicVector->size == 0)
        return -1;
    for (int i = positionToDelete; i < dynamicVector->size; i++)
        dynamicVector->elements[i] = dynamicVector->elements[i + 1];
    dynamicVector->size--;
    return 1;
}

int updateByPosition(Dynamic_array * dynamicVector, int positionToUpdate, TElem elementToUpdateWith)
{
    if (positionToUpdate < 0 || positionToUpdate >= dynamicVector->size || dynamicVector->size == 0)
        return -1;
    dynamicVector->elements[positionToUpdate] = elementToUpdateWith;
    return 1;
}

TElem getElementByPosition(Dynamic_array * dynamicVector, int positionToFind)
{
  //  if (positionToFind < 0 || positionToFind >= dynamicVector->size || dynamicVector->size == 0)
//        return -1;
    return dynamicVector->elements[positionToFind];
}


int almostFullCapacityUsed(Dynamic_array * dynamicVector)
{
    return dynamicVector->size == dynamicVector->capacity;
}