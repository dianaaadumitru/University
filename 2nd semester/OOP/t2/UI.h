#pragma once

#include "Person.h"
#include "Measurement.h"
#include "domain.h"
#include "validator.h"
#ifndef T2_913_DUMITRU_DIANA_1_UI_H
#define T2_913_DUMITRU_DIANA_1_UI_H


class UI {
public:
    Person *domain;
    explicit UI(Person *domain);
    void run();

private:
    void display();
    void add();
    static void printMenu();
};


#endif //T2_913_DUMITRU_DIANA_1_UI_H
