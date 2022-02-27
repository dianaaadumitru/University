#pragma once
#include "domain.h"
#include "Measurement.h"
#include "BMI.h"
#include "BP.h"
#include <vector>


class Person {
private:
    std::vector<Domain>* person;
public:
    Person();
//    Person(const Person& p);

    bool addMeasurement(const Domain& d);

    std::vector<Domain>* getAllMeasurements() {return this->person;}
};



