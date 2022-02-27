#pragma once
#include "repo.h"

typedef struct{
    Repo *repo;
}Service;


Service *create_service(Repo *repo);

void destroy_service(Service *service);

int add_new_holiday(Service * service, char *type, char *destination, char *dep_time, int price, int current_undo);

void holidays_by_type(Dynamic_array * holidays, char given_type[], char given_date[], char holidays_array[]);

int remove_holiday_service(Service *service, char *dest, char *dep_time, int current_undo);

int update_holiday_service(Service * service, char *new_elem, char *dest, char *dep_time, int op, int current_undo);

Dynamic_array *return_holidays_with_a_given_dest_service(Service *service, char *dest, int *size, int op);

int undo_service(Service *service);

int redo_service(Service *service);

Dynamic_array *return_holidays_sorted_by_month_service(Service *service, char *dest, int *size);

void holidays_that_are_at_least(Dynamic_array *holidays, char *given_type, int given_price, char *new_array);
