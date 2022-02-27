#include <iostream>
#include <QTWidgets/QApplication>
#include "gui.h"
#include <assert.h>

void testCountCars(){
    Repo *repo = new Repo();
    Service *service = new Service(repo);
    std::vector<Car> *cars = service->getCars();
    assert(service->countCars("Audi") == 2);
    assert(service->countCars("") == 0);
}

int main(int argc, char *argv[]) {
    testCountCars();
    QApplication a(argc, argv);
    Repo *repo = new Repo();
    Service *service = new Service(repo);
    gui gui{service};
////    gui gui{service};
    gui.show();
    return a.exec();
}
