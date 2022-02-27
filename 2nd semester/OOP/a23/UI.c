#include "UI.h"
#include <stdio.h>
#include <stdlib.h>

void print_menu()
///function that prints the menu
{
    printf("\n1. Display all tourism offers\n"
           "2. Add an offer\n"
           "3. Delete an offer\n"
           "4. Update an offer\n"
           "5. Display all tourism offers whose destinations contain a given string (if the string is empty, all destinations are considered), and show them sorted ascending by price\n"
           "6. For a given destination, display all offers, sorted ascending by departure month\n"
           "7. Display all offers of a given type, having their departure after a given date\n"
           "8. Undo\n"
           "9. Redo\n"
           "10. Display all offers of a given type that costs at least a given price\n"
           "0. Exit\n");
}

Holidays_UI* create_UI(Service * service)
{
    Holidays_UI  *holiday_ui = (Holidays_UI*)malloc(sizeof(Holidays_UI));
    holiday_ui->holidays_service = service;
    return holiday_ui;
}

void destroy_ui(Holidays_UI * holiday_ui)
{
    destroy_service(holiday_ui->holidays_service);
    free(holiday_ui);
}

void list_ui(Holidays_UI *holidays_ui)
{
    if (holidays_ui->holidays_service->repo->holidays_repo->size == 0)
    {
        printf("\nNo offers available!\n");
        return;
    }
    for(int i=0; i<holidays_ui->holidays_service->repo->holidays_repo->size; i++)
        printf("%s %s %s %d\n", get_type(holidays_ui->holidays_service->repo->holidays_repo->elements[i]), get_destination(holidays_ui->holidays_service->repo->holidays_repo->elements[i]), get_dep_time(holidays_ui->holidays_service->repo->holidays_repo->elements[i]), get_price(holidays_ui->holidays_service->repo->holidays_repo->elements[i]));
}

void add_ui(Holidays_UI * holidays_ui)
{
    char type[31], dest[51], dep_time[11];
    int price;

    printf("\nWhat kind of offer do you want to add? \n1. City break \n2. Seaside \n3.Mountain \n>>");
    int op;
    scanf("%d", &op);
    if(op == 1)
        strcpy(type, "city break");
    else if (op == 2)
        strcpy(type, "seaside");
    else if (op ==3)
        strcpy(type, "mountain");
    else
    {
        printf("\nInvalid input!\n");
        return;
    }

    printf("\ndestination: ");
    scanf("%s", dest);
    printf("\ndeparture time: ");
    scanf("%s", dep_time);
    if(date_validity(dep_time) == 0)
    {
        printf("Invalid date!\n");
        return;
    }

    printf("\nprice: ");
    scanf("%d", &price);
    int ok = add_new_holiday(holidays_ui->holidays_service, type, dest, dep_time, price, 1);
    if(ok == -1)
        printf("\nyou cannot have 2 offers with the same destination and departure date\n");
    else
        printf("\nOffer added\n");

}

void remove_ui(Holidays_UI * holidays_ui)
{
    char dest[31], dep_time[11];
    printf("\ndestination: ");
    scanf("%s", dest);

    printf("\ndeparture time: ");
    scanf("%s", dep_time);
    if(date_validity(dep_time) == 0)
    {
        printf("Invalid date!\n");
        return;
    }
    int ok = remove_holiday_service(holidays_ui->holidays_service, dest, dep_time, 1);
    if(ok == -1)
        printf("\nThis offer does not exist\n");
    else
        printf("\nOffer deleted\n");
}

void update_ui(Holidays_UI * holidays_ui)
{
    char dest[31], dep_time[11];

    printf("\ndestination: ");
    scanf("%s", dest);

    printf("\ndeparture time: ");
    scanf("%s", dep_time);
    if(date_validity(dep_time) == 0)
    {
        printf("Invalid date!\n");
        return;
    }
    int ok = search_for_holiday(holidays_ui->holidays_service->repo, dest, dep_time);
    if(ok == -1) {
        printf("This offer does not exist!");
        return;
    }
    char new_elem[41];

    printf("\nWhat do you want to update?\n 1. Type\n 2. Destination\n 3. Departure time\n 4. Price\n");
    int op;
    printf(">> ");
    scanf("%d", &op);
    if (op == 1)
    {
        printf("\nChoose the new type: \n1. City break \n2. Seaside \n3.Mountain \n>>");
        int op;
        scanf("%d", &op);
        if(op == 1)
            strcpy(new_elem, "city break");
        else if (op == 2)
            strcpy(new_elem, "seaside");
        else
            strcpy(new_elem, "mountain");
    }
    else if (op == 2)
    {
        printf("new destination: ");
        scanf("%s", new_elem);
    }
    else if (op == 3)
    {
        printf("new departure time: ");
        scanf("%s", new_elem);
        if(date_validity(new_elem) == 0)
        {
            printf("Invalid date!\n");
            return;
        }
    }
    else if (op == 4)
    {
        printf("\nnew price: ");
        scanf("%s", new_elem);
    }
    else
    {
        printf("Invalid input!");
        return;
    }

    int x = update_holiday_service(holidays_ui->holidays_service, new_elem, dest, dep_time, op, 1);
    if(x == 1)
        printf("Offer updated!");
    else
        printf("You cannot have 2 offers with the same destination and departure date");
}

void sort_by_month_ui(Holidays_UI * holidays_ui)
{
    char dest_array[51];
    printf("\ndestination: ");
    scanf("%s", dest_array);
    int size = 0;
    Dynamic_array * list_holidays = return_holidays_sorted_by_month_service(holidays_ui->holidays_service, dest_array, &size);
    if(size == 0)
    {
        printf("\nNo offers available\n");
        return;
    }
    else
        for (int i =0; i < size; i++)
            printf("%s %s %s %d\n", get_type(list_holidays->elements[i]), get_destination(list_holidays->elements[i]), get_dep_time(list_holidays->elements[i]), get_price(list_holidays->elements[i]));
    destroy_holidays_repo(list_holidays);
}

void show_sorted_list_ui(Holidays_UI *holidays_ui)
{
    char dest_array[51];
    printf("\ndestination: ");
    scanf("%s", dest_array);
    int size = 0, op;
    printf("\nDo you want to display the list ascending or descending?\n 1. Ascending\n 2. Descending\n>>");
    scanf("%d", &op);
    Dynamic_array * list_holidays = return_holidays_with_a_given_dest_service(holidays_ui->holidays_service, dest_array, &size, op);
    for (int i =0; i < size; i++)
        printf("%s %s %s %d\n", get_type(list_holidays->elements[i]), get_destination(list_holidays->elements[i]), get_dep_time(list_holidays->elements[i]), get_price(list_holidays->elements[i]));
    destroy_holidays_repo(list_holidays);
}

void show_list_by_type(Dynamic_array * holidays, char new_array[])
{
    char given_type[31], given_date[12];
    printf("\nChoose the type: \n1. City break \n2. Seaside \n3.Mountain \n>>");
    int op;
    scanf("%d", &op);
    if(op == 1)
        strcpy(given_type, "city break");
    else if (op == 2)
        strcpy(given_type, "seaside");
    else if (op == 3)
        strcpy(given_type, "mountain");
    else
    {
        printf("\nInvalid input!\n");
        return;
    }

    printf("\ndate: ");
    scanf("%s", given_date);
    if(date_validity(given_date) == 0)
    {
        printf("Invalid date!\n");
        return;
    }

    holidays_by_type(holidays, given_type, given_date, new_array);
}

void list_by_price_ui(Dynamic_array *holidays, char new_array[])
{
    char given_type[31];
    int price;
    printf("\nChoose the type: \n1. City break \n2. Seaside \n3.Mountain \n>>");
    int op;
    scanf("%d", &op);
    if(op == 1)
        strcpy(given_type, "city break");
    else if (op == 2)
        strcpy(given_type, "seaside");
    else if (op == 3)
        strcpy(given_type, "mountain");
    else
    {
        printf("\nInvalid input!\n");
        return;
    }
    printf("\nprice: ");
    scanf("%d", &price);
    holidays_that_are_at_least(holidays, given_type, price, new_array);
}

void undo_ui(Holidays_UI*holidays_ui)
{
    int ok = undo_service(holidays_ui->holidays_service);
    if (ok == -1)
        printf("\nYou cannot undo anymore!\n");
    else
        printf("\nUndo successful!\n");
}

void redo_ui(Holidays_UI *holidays_ui)
{
    int ok = redo_service(holidays_ui->holidays_service);
    if(ok == -1)
        printf("\nYou cannot redo anymore\n");
    else
        printf("\nRedo successful\n");
}

void run(Holidays_UI * holidays_ui)
{
    int done = 1, choice;

    while(done) // loop for the menu, the user can choose the command and the program stops when he chooses to exit
    {
        print_menu();
        printf("\n>>");
        scanf("%d", &choice);
        if (choice == 0) {
            destroy_ui(holidays_ui);
            done = 0;
        } else if (choice == 1)
        {
            list_ui(holidays_ui);
        }
        else if (choice == 2)
            add_ui(holidays_ui);
        else if (choice == 3)
            remove_ui(holidays_ui);
        else if (choice == 4)
            update_ui(holidays_ui);
        else if(choice == 5)
            show_sorted_list_ui(holidays_ui);
        else if (choice == 6)
            sort_by_month_ui(holidays_ui);
        else if (choice == 7)
        {
            char new_array[2506] = "";
            show_list_by_type(holidays_ui->holidays_service->repo->holidays_repo, new_array);
            if(strlen(new_array) == 0)
                printf("No offers available!");
            else
                printf("%s", new_array);
            strcpy(new_array, "");
        }
        else if (choice == 8)
            undo_ui(holidays_ui);
        else if (choice == 9)
            redo_ui(holidays_ui);
        else if (choice == 10)
        {
            char new_array[2506] = "";
            list_by_price_ui(holidays_ui->holidays_service->repo->holidays_repo, new_array);
            if(strlen(new_array) == 0)
                printf("No offers available!");
            else
                printf("%s", new_array);
            strcpy(new_array, "");
        }
        else
            printf("Invalid input!\n");
    }
}
