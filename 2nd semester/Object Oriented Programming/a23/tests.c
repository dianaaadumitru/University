#include "tests.h"
//
void test_all()
{
    test_holiday();
    test_array();
    test_repo();
    test_service();
    test_destroy_UI();
}

void test_holiday()
{
    test_create_holiday();
    test_destroy_holiday();
}

void test_array()
{
    test_create_array();
    test_destroy_array();
    test_copy_array();
    test_size_of_array();
    test_append_array();
    test_delete_array();
    test_update_array();
    test_get_elements_array();
    test_capacity_array();

}

void test_repo()
{
    test_create_repo();
    test_search_for_holiday();
    test_add_holiday();
    test_remove_holiday();
    test_update_holiday();
    test_sort_holidays();
    test_undo();
    test_redo();
    test_date();
}

void test_service()
{
    test_create_service();
    test_destroy_service();
    test_add_holiday_service();
    test_remove_holiday_service();
    test_update_holiday_service();
}


void test_create_holiday()
{
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    assert(strcmp(get_type(holiday), "seaside") == 0);
    assert(strcmp(get_destination(holiday), "Maldives") == 0);
    assert(strcmp(get_dep_time(holiday), "12.12.2021") == 0);
    assert(get_price(holiday) == 1234);
    destroy_holiday(holiday);
}

void test_destroy_holiday()
{
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    destroy_holiday(holiday);
}


void test_create_array()
{
    Dynamic_array *array = create_array(5);
    assert(array->size == 0);
    assert(array->capacity == 5);
    destroy_array(array);
}

void test_destroy_array()
{
    Dynamic_array *array = create_array(5);
    destroy_array(array);
}

void test_copy_array()
{
    Dynamic_array *array = create_array(5);
    Dynamic_array *copy = copyArray(array);
    assert(array->size == copy->size);
    assert(array->capacity == copy->capacity);
    destroy_array(array);
    destroy_array(copy);
}

void test_size_of_array()
{
    Dynamic_array *array = create_array(5);
    assert(size_of_array(array) == array->size);
    destroy_array(array);
}

void test_append_array()
{
    Dynamic_array * array = create_array(5);
    int append[] = {1, 2, 3};
    for(int i = 0; i < 3; i++)
    {
        appendDynamicallyVector(array, &append[i]);
        assert(array->size == i + 1);
    }
    destroy_array(array);

}

void test_delete_array()
{
    Dynamic_array * array = create_array(5);
    int append[] = {1, 2, 3};
    for(int i = 0; i < 3; i++)
        appendDynamicallyVector(array, &append[i]);
    deleteByPosition(array, 2);
    assert(array->size == 2);
    destroy_array(array);
}

void test_update_array()
{
    Dynamic_array * array = create_array(5);
    int append[] = {1, 2, 3};
    for(int i = 0; i < 3; i++)
        appendDynamicallyVector(array, &append[i]);
    int x = updateByPosition(array, 0, &append[2]);
    assert(x == 1);
    destroy_array(array);
}

void test_get_elements_array()
{
    Dynamic_array * array = create_array(5);
    int append[] = {1, 2, 3};
    for(int i = 0; i < 3; i++)
        appendDynamicallyVector(array, &append[i]);
    TElem *value = getElementByPosition(array, 0);
    assert(value);
    destroy_array(array);
}

void test_capacity_array()
{
    Dynamic_array * array = create_array(1);
    int value = 10;
    assert(almostFullCapacityUsed(array) == 0);
    appendDynamicallyVector(array, &value);
    assert(almostFullCapacityUsed(array) == 1);
    destroy_array(array);
}


void test_create_repo()
{
    Repo  *repo = create_repo(5);
    assert(size_of_array(repo->holidays_repo) == 0);
    destroy_repo(repo);
}


void test_destroy_repo()
{
    Repo  *repo = create_repo(5);
    destroy_repo(repo);
}

void test_search_for_holiday()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    add_holiday(repo, holiday, 0);
    int ok = search_for_holiday(repo, "Maldives", "12.12.2021");
    assert(ok == 0);
    destroy_repo(repo);
}

void test_add_holiday()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    int ok = add_holiday(repo, holiday, 0);
    assert(size_of_array(repo->holidays_repo)==1);
    assert(ok == 1);
    destroy_repo(repo);
}

void test_remove_holiday()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    add_holiday(repo, holiday, 0);
    int ok = remove_holiday(repo, "Maldives", "12.12.2021", 0);
    assert(size_of_array(repo->holidays_repo) == 0);
    assert(ok == 1);
    //destroy_holiday(holiday);
    destroy_repo(repo);
}

void test_update_holiday()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    add_holiday(repo, holiday, 0);
    int ok = update_holiday(repo, "mountain", "Maldives", "12.12.2021", 1, 0);
    assert(strcmp(get_type(repo->holidays_repo->elements[0]), "mountain") == 0);
    assert(ok == 1);
    //destroy_holiday(holiday);
    destroy_repo(repo);
}

void test_sort_holidays()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    add_holiday(repo, holiday, 0);
    Holiday *holiday2 = create_holiday("city break", "Paris", "13.09.2021", 33330);
    add_holiday(repo, holiday2, 0);
    sort_holidays(repo->holidays_repo);
    assert(strcmp(get_type(repo->holidays_repo->elements[0]), "seaside") == 0);
    sort_holidays_descending(repo->holidays_repo);
    assert(strcmp(get_type(repo->holidays_repo->elements[0]), "city break") == 0);
//    destroy_holiday(holiday);
//    destroy_holiday(holiday2);
    destroy_repo(repo);
}

void test_undo()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    add_holiday(repo, holiday, 0);
    Holiday *holiday2 = create_holiday("city break", "Paris", "13.09.2021", 33330);
    add_holiday(repo, holiday2, 0);
    undo(repo);
    assert(size_of_array(repo->holidays_repo) == 1);

    undo(repo);
    assert(size_of_array(repo->holidays_repo) == 0);

    destroy_repo(repo);
}

void test_redo()
{
    Repo  *repo = create_repo(5);
    Holiday * holiday = create_holiday("seaside", "Maldives", "12.12.2021", 1234);
    add_holiday(repo, holiday, 0);
    Holiday *holiday2 = create_holiday("city break", "Paris", "13.09.2021", 33330);
    add_holiday(repo, holiday2, 0);
    undo(repo);
    undo(repo);

    redo(repo);
    assert(size_of_array(repo->holidays_repo) == 1);
    redo(repo);
    assert(size_of_array(repo->holidays_repo) == 2);
    destroy_repo(repo);
}

void test_date()
{
    int ok = date_validity("12.12.2012");
    assert(ok == 1);
    int ok2 = date_validity("32.12.2021");
    assert(ok2 == 0);
    int ok3 = date_validity("23.14.2020");
    assert(ok3 == 0);
    int ok4 = date_validity("12312.2020");
    assert(ok4 == 0);
}


void test_create_service()
{
    Repo  *repo = create_repo(5);
    Service *service = create_service(repo);
    assert(size_of_array(service->repo->holidays_repo) == 0);
    destroy_service(service);
}

void test_destroy_service()
{
    Repo  *repo = create_repo(5);
    Service *service = create_service(repo);
    destroy_service(service);
}

void test_add_holiday_service()
{
    Repo  *repo = create_repo(5);
    Service *service = create_service(repo);
    add_new_holiday(service, "seaside", "Fiji", "02.04.2021", 8500, 1);
    assert(size_of_array(service->repo->holidays_repo) == 1);
    destroy_service(service);
}

void test_remove_holiday_service()
{
    Repo  *repo = create_repo(5);
    Service *service = create_service(repo);
    add_new_holiday(service, "seaside", "Fiji", "02.04.2021", 8500, 0);
    remove_holiday_service(service, "Fiji", "02.04.2021", 0);
    assert(size_of_array(service->repo->holidays_repo) == 0);
    destroy_service(service);
}

void test_update_holiday_service()
{
    Repo  *repo = create_repo(5);
    Service *service = create_service(repo);
    add_new_holiday(service, "seaside", "Fiji", "02.04.2021", 8500, 0);
    int ok = update_holiday_service(service, "Paris", "Fiji", "02.04.2021", 2, 0);
    assert(ok == 1);
    assert(strcmp(get_destination(service->repo->holidays_repo->elements[0]), "Paris") == 0);
    destroy_service(service);
}


void test_destroy_UI()
{
    Repo  *repo = create_repo(5);
    Service *service = create_service(repo);
    Holidays_UI *ui = create_UI(service);
    destroy_ui(ui);
}