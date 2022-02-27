
#include "service.h"
#include <stdlib.h>
#include <string.h>

Service *create_service(Repo *repo)
{
    Service* service = (Service*)malloc(sizeof(Service));
    service->repo = repo;
    return service;
}

void destroy_service(Service *service)
{
    destroy_repo(service->repo);
    free(service);
}

int add_new_holiday(Service * service, char *type, char *destination, char *dep_time, int price, int current_undo)
///create a new holiday with the given parameters then add it to the list
{
    Holiday * holiday = create_holiday(type, destination, dep_time, price);
    return add_holiday(service->repo, holiday, current_undo);
}

int remove_holiday_service(Service *service, char *dest, char *dep_time, int current_undo)
{
    return remove_holiday(service->repo, dest, dep_time, current_undo);
}

int update_holiday_service(Service * service, char *new_elem, char *dest, char *dep_time, int op, int current_undo)
{
    return update_holiday(service->repo, new_elem, dest, dep_time, op, current_undo);
}

Dynamic_array *return_holidays_with_a_given_dest_service(Service *service, char *dest, int *size, int op)
{
    return return_holidays_with_a_given_dest(service->repo, dest, size, op);
}

Dynamic_array *return_holidays_sorted_by_month_service(Service *service, char *dest, int *size)
{
    return return_holidays_sorted_by_month(service->repo, dest, size);
}

int undo_service(Service *service)
{
    return undo(service->repo);
}

int redo_service(Service *service)
{
    return redo(service->repo);
}

void holidays_by_type(Dynamic_array * holidays, char given_type[], char given_date[], char holidays_array[])
{
    char d[3], m[3], y[5], day[3], month[3], year[5], date[12];
    d[0] = given_date[0];
    d[1] = given_date[1];
    d[2] = '\0';
    int d1 = atoi(d);

    m[0] = given_date[3];
    m[1] = given_date[4];
    m[2] = '\0';
    int m1 = atoi(m);

    y[0] = given_date[6];
    y[1] = given_date[7];
    y[2] = given_date[8];
    y[3] = given_date[9];
    y[4] = '\0';
    int y1 = atoi(y);

    //printf("%d %d %d", d1,m1,y1);

    for(int i = 0; i< holidays->size; i++)
    {
        strcpy(date, get_dep_time(holidays->elements[i]));
        day[0] = date[0];
        day[1] = date[1];
        day[2] = '\0';
        int day1 = atoi(day);

        month[0] = date[3];
        month[1] = date[4];
        month[2] = '\0';
        int month1 = atoi(month);

        year[0] = date[6];
        year[1] = date[7];
        year[2] = date[8];
        year[3] = date[9];
        year[4] = '\0';
        int year1 = atoi(year);

        if(strcmp(get_type(holidays->elements[i]), given_type) == 0) {
            if((y1 < year1) || (y1 == year1 && m1 < month1) || (y1 == year1 && m1 == month1 && d1 < day1)) {
                tostring(holidays->elements[i], holidays_array);
            }
        }
    }

}

void holidays_that_are_at_least(Dynamic_array *holidays, char *given_type, int given_price, char *new_array)
{
    for(int i = 0; i <holidays->size; i++)
        if(strcmp(get_type(holidays->elements[i]), given_type) == 0)
            if(given_price < get_price(holidays->elements[i]))
                tostring(holidays->elements[i], new_array);
}
