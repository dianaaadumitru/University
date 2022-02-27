#pragma once
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct
{
    char *type;
    char *destination;
    char *dep_time;
    int price;
}Holiday;

Holiday * create_holiday(char *type, char *destination, char *dep_time, int price);

void destroy_holiday(Holiday* holiday);

char * get_type(Holiday * holiday);

char * get_destination(Holiday * holiday);

char * get_dep_time(Holiday * holiday);

int get_price(Holiday * holiday);

void set_price(Holiday * holiday, int new_price);

Holiday * copy_holiday(Holiday * holiday);

void tostring(Holiday * holiday, char new_string[]);
