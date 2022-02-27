#include "holiday.h"
#include <string.h>
#include <stdlib.h>

Holiday * create_holiday(char *type, char *destination, char *dep_time, int price)
{
    Holiday *holiday = (Holiday*)malloc(sizeof (Holiday));
    holiday->type = (char*)malloc(sizeof(char) * (strlen(type) + 1));
    holiday->destination = (char*)malloc(sizeof(char) * (strlen(destination) + 1));
    holiday->dep_time = (char*)malloc(sizeof(char) * (strlen(dep_time) + 1));
    strcpy(holiday->type, type);
    strcpy(holiday->destination, destination);
    strcpy(holiday->dep_time, dep_time);
    holiday->price = price;
    return holiday;
}

void destroy_holiday(Holiday* holiday)
{
    if(holiday == NULL)
        return;

    free(holiday->type);
    free(holiday->destination);
    free(holiday->dep_time);
    free(holiday);
}


char * get_type(Holiday * holiday)
{
    return holiday->type;
}

char * get_destination(Holiday * holiday)
{
    return holiday->destination;
}

char * get_dep_time(Holiday * holiday)
{
    return holiday->dep_time;
}

int get_price(Holiday * holiday)
{
    return holiday->price;
}

Holiday * copy_holiday(Holiday * holiday)
{
    Holiday * copy = (Holiday*)malloc(sizeof (Holiday));
    copy->type = (char*)malloc(sizeof (char)*(strlen(holiday->type)+1));
    strcpy(copy->type, holiday->type);
    copy->destination = (char*)malloc(sizeof (char)*(strlen(holiday->destination)+1));
    strcpy(copy->destination, holiday->destination);
    copy->dep_time = (char*)malloc(sizeof (char)*(strlen(holiday->dep_time)+1));
    strcpy(copy->dep_time, holiday->dep_time);
    copy->price = holiday->price;

    return copy;
}

void tostring(Holiday * holiday, char new_string[])
{
    char aux[100];
    strcpy(aux, holiday->type);
    strcat(new_string, aux);
    strcat(new_string, " ");
    strcpy(aux, holiday->destination);
    strcat(new_string, aux);
    strcat(new_string, " ");
    strcpy(aux, holiday->dep_time);
    strcat(new_string, aux);
    strcat(new_string, " ");
//    int x = holiday->price + '0';
    itoa(holiday->price, aux, 10);
//    strcat(new_string, x);
    strcat(new_string, "\n");
}

void set_price(Holiday * holiday, int new_price)
{
    holiday->price = new_price;
}
