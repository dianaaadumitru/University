#include <stdio.h>
#include "UI.h"
#include "tests.h"


int main(){
    test_all();

    Repo * repo = create_repo(15);
    Service *service = create_service(repo);
    Holidays_UI *ui = create_UI(service);


    add_new_holiday(ui->holidays_service, "city break", "Paris", "17.05.2021", 870, 1);
    add_new_holiday(ui->holidays_service, "seaside", "Fiji", "02.04.2021", 8500, 1);
    add_new_holiday(ui->holidays_service, "city break", "Tokyo", "19.04.2021", 21500, 1);
    add_new_holiday(ui->holidays_service, "seaside", "Caribbean ", "29.06.2021", 62000, 1);
    add_new_holiday(ui->holidays_service, "seaside", "Maldives", "19.11.2021", 8900, 1);
    add_new_holiday(ui->holidays_service, "seaside", "Seychelles", "11.03.2021", 30000, 1);
    add_new_holiday(ui->holidays_service, "mountain", "Alpes", "04.09.2021", 695, 1);
    add_new_holiday(ui->holidays_service, "city break", "Sydney", "23.12.2021", 700,1);
    add_new_holiday(ui->holidays_service, "mountain", "Switzerland", "10.10.2021", 6345, 1);
    add_new_holiday(ui->holidays_service, "city break", "Sydney", "12.08.2021", 1150, 1);

    //destroy_ui(ui);
    run(ui);
    int x = _CrtDumpMemoryLeaks();

    printf("\nMemory leak: %d", x);
    return 0;
}