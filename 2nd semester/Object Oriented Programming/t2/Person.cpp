
#include "Person.h"
#include <iostream>
#include <algorithm>
using namespace std;

Person::Person() {
    this->person = new std::vector<Domain>();
}

//Person::Person(const Person &p) {
//    this->person = new std::vector<Domain>();
//
//    for (auto & it: *(p.person)){
//        person->push_back(it);
//    }
//    return *this;
//}

bool Person::addMeasurement(const Domain &d) {
    this->person->push_back(d);
    return true;
}