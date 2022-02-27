#include "repo.h"
#include "array.h"
#include <string.h>


Repo* create_repo(int capacity)
{
    Repo *repo = (Repo*)malloc((sizeof(Repo)));
    repo->holidays_repo = create_array(capacity);
    repo->history = create_array(33);
    repo->index_of_history = -1;

    return repo;
}

void destroy_holidays_repo(Dynamic_array* holidays_repo)
{
    for(int i = 0; i < holidays_repo->size; i++)
        destroy_holiday(holidays_repo->elements[i]);
    destroy_array(holidays_repo);
}

void destroy_history_list(Dynamic_array* history)
{
    Dynamic_array * list_of_holidays;
    for(int i = 0; i<history->size; i++)
    {
        list_of_holidays = (Dynamic_array*)getElementByPosition(history, i);
        destroy_holidays_repo(list_of_holidays);
//        destroy_holiday(history->elements[i]);
    }
    destroy_array(history);
}

void destroy_repo(Repo *repo)
{
    destroy_holidays_repo(repo->holidays_repo);
    destroy_history_list(repo->history);
    free(repo);
}

Dynamic_array *get_holidays_copy(Repo* repo, Dynamic_array * holidays)
{
    Dynamic_array *aux = create_array(size_of_array(holidays));
    Holiday *holiday_to_check;
    Holiday *holiday_to_append;
    for(int i = 0; i < size_of_array(holidays); i++)
    {
        holiday_to_check = getElementByPosition(holidays, i);
        holiday_to_append = create_holiday(holiday_to_check->type, holiday_to_check->destination, holiday_to_check->dep_time, holiday_to_check->price);
        appendDynamicallyVector(aux, holiday_to_append);
    }
    return aux;
}

void append_repo_in_history(Repo *repo)
{
    Dynamic_array  * copy = get_holidays_copy(repo, repo->holidays_repo);
    appendDynamicallyVector(repo->history, copy);
    repo->index_of_history++;
}

void remove_after_this_state(Repo * repo)
{
    if(repo->index_of_history < size_of_array(repo->history) - 1)
    {
        Dynamic_array* aux;
        while(repo->index_of_history < size_of_array(repo->history) - 1)
        {
            aux = (Dynamic_array*) getElementByPosition(repo->history, size_of_array(repo->history) - 1);
            destroy_holidays_repo(aux);
            destroy_array(aux);
            deleteByPosition(repo->history, size_of_array(repo->history) - 1);

        }
    }
}

int search_for_holiday(Repo * repo, char *destination, char *dep_time)
/// searches in the array for a holiday having a certain destination and a certain departure time
/// \param holidays the array
/// \param destination the given destination
/// \param dep_time the given departure date
/// \return the position if the element is found, -1 otherwise
{
    //Holiday * holiday;
    for(int i = 0; i < repo->holidays_repo->size; i++)
        if(strcmp(get_destination(repo->holidays_repo->elements[i]), destination) == 0 && strcmp(get_dep_time(repo->holidays_repo->elements[i]), dep_time) == 0)
            return i;
    return -1;

}

int add_holiday(Repo * repo, Holiday * new_holiday, int current_undo)
/// add a new holiday to the list
///returns -1 if that holiday already exists
{
    int pos = search_for_holiday(repo, new_holiday->destination, new_holiday->dep_time);
    if(pos != -1) {
        destroy_holiday(new_holiday);
        return -1;
    }

    if (current_undo == 1) {
        //printf("%d %d\n", repo->index_of_history, size_of_array(repo->history));
        remove_after_this_state(repo);
        //printf("%d %d\n", repo->index_of_history, size_of_array(repo->history));
    }
    appendDynamicallyVector(repo->holidays_repo, new_holiday);
    append_repo_in_history(repo);
    //printf("%d %d\n", repo->index_of_history, size_of_array(repo->history));
    //destroy_holiday(new_holiday);
    return 1;
}

int remove_holiday(Repo *repo, char *destination, char *dep_time, int current_undo)
///deletes a holiday from the list
///returns -1 if the element you want to delete does not exist
{
    int pos = search_for_holiday(repo, destination, dep_time);
    if(pos == -1)
        return -1;
    if(current_undo == 1)
        remove_after_this_state(repo);
    Holiday *trash_holiday = getElementByPosition(repo->holidays_repo, pos);
    destroy_holiday(trash_holiday);
    deleteByPosition(repo->holidays_repo, pos);
    append_repo_in_history(repo);
    return 1;
}

int update_holiday(Repo * repo, char new_elem[], char destination[], char dep_time[], int op, int current_undo)
///updates an offer
///returns 1 if the offer was updates
///returns -1 if the offer you want to update does not exist in the list
///returns 0 if you already have an offer with the same destination and departure time
{
    int pos = search_for_holiday(repo, destination, dep_time);
    if(pos == -1)
    {
        return - 1;
    }

    char type[31];
    int price = 0;

    if(op == 1)
        strcpy(type, new_elem);
    else if (op == 2)
    {
        int ok = search_for_holiday(repo, new_elem, get_dep_time(repo->holidays_repo->elements[pos]));
        if(ok != -1)
            return 0;
        strcpy(destination, new_elem);
    }
    else if (op == 3)
    {
        int ok = search_for_holiday(repo, get_destination(repo->holidays_repo->elements[pos]) ,new_elem);
        if(ok != -1)
            return 0;
        strcpy(dep_time, new_elem);
    }
    else
    {
        int price2 = atoi(new_elem);
        price = price2;
    }
    Holiday *updated_holiday = create_holiday(type, destination, dep_time, price);

    if (current_undo == 1)
        remove_after_this_state(repo);
    destroy_holiday(repo->holidays_repo->elements[pos]);
    updateByPosition(repo->holidays_repo, pos, updated_holiday);
    append_repo_in_history(repo);

    return 1;
}

void sort_holidays(Dynamic_array * holidays)
{
    int ok;
    do {
        ok = 1;
        for (int i = 0; i < holidays->size - 1; i++)
            if (get_price(holidays->elements[i]) > get_price(holidays->elements[i + 1])) {
                Holiday *aux;
                aux = copy_holiday(holidays->elements[i]);
                destroy_holiday(holidays->elements[i]);
                holidays->elements[i] = copy_holiday(holidays->elements[i + 1]);
                destroy_holiday(holidays->elements[i + 1]);
                holidays->elements[i + 1] = copy_holiday(aux);
                destroy_holiday(aux);
                ok = 0;
            }
    } while (ok == 0);
}

void sort_holidays_descending(Dynamic_array * holidays)
{
    int ok;
    do {
        ok = 1;
        for (int i = 0; i < holidays->size - 1; i++)
            if (get_price(holidays->elements[i]) < get_price(holidays->elements[i + 1])) {
                Holiday *aux;
                aux = copy_holiday(holidays->elements[i]);
                destroy_holiday(holidays->elements[i]);
                holidays->elements[i] = copy_holiday(holidays->elements[i + 1]);
                destroy_holiday(holidays->elements[i + 1]);
                holidays->elements[i + 1] = copy_holiday(aux);
                destroy_holiday(aux);
                ok = 0;
            }
    } while (ok == 0);
}

Dynamic_array *return_holidays_with_a_given_dest(Repo *repo, char *dest, int *size, int op)
{
    int length_of_list = 0;
    Holiday *holiday_to_check;
    Holiday *holiday_to_append;
    for (int i = 0; i < size_of_array(repo->holidays_repo); i++) {
        holiday_to_check = getElementByPosition(repo->holidays_repo, i);
        if(strstr(holiday_to_check->destination, dest))
        {
            length_of_list++;
        }
    }
    Dynamic_array *list_of_holidays = create_array(length_of_list);
    if (length_of_list == 0)
    {
        list_of_holidays = copyArray(repo->holidays_repo);
        if (op == 1)
            sort_holidays(list_of_holidays);
        else
            sort_holidays_descending(list_of_holidays);
        *size = size_of_array(repo->holidays_repo);
        return list_of_holidays;
    }
    for (int i = 0; i < size_of_array(repo->holidays_repo); i++)
    {
        holiday_to_check = getElementByPosition(repo->holidays_repo, i);
        if(strstr(holiday_to_check->destination, dest))
        {
            holiday_to_append = create_holiday(holiday_to_check->type, holiday_to_check->destination, holiday_to_check->dep_time, holiday_to_check->price);
            appendDynamicallyVector(list_of_holidays, holiday_to_append);
        }

    }
    *size = length_of_list;
    if (op == 1)
        sort_holidays(list_of_holidays);
    else
        sort_holidays_descending(list_of_holidays);
    return list_of_holidays;
}

void sort_holidays_by_month(Dynamic_array * holidays)
{
    int ok;
    do {
        ok = 1;
        for (int i = 0; i < holidays->size - 1; i++)
        {
            char month1[3], month2[3], date1[12], date2[12];
            strcpy(date1, get_dep_time(holidays->elements[i]));
            strcpy(date2, get_dep_time(holidays->elements[i+1]));
            month1[0] = date1[3];
            month1[1] = date1[4];
            month1[2] = '\0';
            int m1 = atoi(month1);
            month2[0] = date2[3];
            month2[1] = date2[4];
            month2[2] = '\0';
            int m2 = atoi(month2);
            if(m1 > m2)
            {
                Holiday *aux;
                aux = copy_holiday(holidays->elements[i]);
                destroy_holiday(holidays->elements[i]);
                holidays->elements[i] = copy_holiday(holidays->elements[i + 1]);
                destroy_holiday(holidays->elements[i + 1]);
                holidays->elements[i + 1] = copy_holiday(aux);
                destroy_holiday(aux);
                ok = 0;
            }
        }

    }while(ok==0);
}

Dynamic_array *return_holidays_sorted_by_month(Repo * repo, char *dest, int *size)
{
    int length_of_list = 0;
    Holiday *holiday_to_check;
    Holiday *holiday_to_append;
    for (int i = 0; i < size_of_array(repo->holidays_repo); i++) {
        holiday_to_check = getElementByPosition(repo->holidays_repo, i);
        if(strcmp(holiday_to_check->destination, dest) == 0)
        {
            length_of_list++;
        }
    }
    Dynamic_array *list_of_holidays = create_array(length_of_list);
    for (int i = 0; i < size_of_array(repo->holidays_repo); i++)
    {
        holiday_to_check = getElementByPosition(repo->holidays_repo, i);
        if(strcmp(holiday_to_check->destination, dest) == 0)
        {
            holiday_to_append = create_holiday(holiday_to_check->type, holiday_to_check->destination, holiday_to_check->dep_time, holiday_to_check->price);
            appendDynamicallyVector(list_of_holidays, holiday_to_append);
        }

    }
    *size = length_of_list;
    sort_holidays_by_month(list_of_holidays);
    return list_of_holidays;
}

int undo(Repo *repo)
{
    if (repo->index_of_history == -1)
        return -1;

    destroy_holidays_repo(repo->holidays_repo);
    //destroy_array(repo->holidays_repo);
    if(repo->index_of_history == 0)
        repo->holidays_repo = create_array(2);
    else
        repo->holidays_repo = get_holidays_copy(repo, (Dynamic_array*)getElementByPosition(repo->history, repo->index_of_history - 1));

    repo->index_of_history--;
    return 1;
}

int redo(Repo* repo)
{
    if (repo->index_of_history == size_of_array(repo->history) - 1)
        return -1;

    destroy_holidays_repo(repo->holidays_repo);
    //destroy_array(repo->holidays_repo);
    repo->holidays_repo = get_holidays_copy(repo, (Dynamic_array*)getElementByPosition(repo->history, repo->index_of_history + 1));
    repo->index_of_history++;
    return 1;
}


int date_validity(char date[])
///verify if a date is properly introduced
{
    if(strlen(date) != 10)
        return 0;
    char d1 = '0', d2 = '3', d3 = '9';
    //printf("%c %c %c\n", d1, date[3], d2);
    if(d1 > date[0])
        return 0;

    if(date[0] > d2)
        return 0;
    char sep = '.';
    if(date[2]!=sep)
        return 0;
    if(date[5]!=sep)
        return 0;

    if(d1 > date[1])
        return 0;
    if(date[1] > d3)
        return 0;

    if(date[0] == d2 && date[1] > '1')
        return 0;

    if(d1 > date[3])
        return 0;
    if(date[3] > '1')
        return 0;

    if(date[3] == '1' && date[4] > '2')
        return 0;
    return 1;
}


