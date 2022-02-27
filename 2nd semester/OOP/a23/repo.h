#pragma once
#include "holiday.h"
#include "array.h"

typedef struct
{
    Dynamic_array * holidays_repo;
    Dynamic_array *history;
    int index_of_history;
}Repo;

Repo* create_repo(int capacity);

void destroy_holidays_repo(Dynamic_array* holidays_repo);

void destroy_history_list(Dynamic_array* history);

void destroy_repo(Repo *repo);

Dynamic_array *get_holidays_copy(Repo* repo, Dynamic_array * holidays);

void append_repo_in_history(Repo *repo);

void remove_after_this_state(Repo * repo);

int search_for_holiday(Repo * repo, char *destination, char *dep_time);

int add_holiday(Repo * repo, Holiday * new_holiday, int current_undo);

int remove_holiday(Repo *repo, char *destination, char *dep_time, int current_undo);

int update_holiday(Repo * repo, char new_elem[], char destination[], char dep_time[], int op, int current_undo);

Dynamic_array *return_holidays_with_a_given_dest(Repo *repo, char *dest, int *size, int op);

Dynamic_array *return_holidays_sorted_by_month(Repo * repo, char *dest, int *size);

int undo(Repo *repo);

int redo(Repo* repo);

int date_validity(char date[]);

void sort_holidays(Dynamic_array * holidays);

void sort_holidays_descending(Dynamic_array * holidays);