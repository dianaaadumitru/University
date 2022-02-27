//
// Created by diana on 5/23/2021.
//

#include "Service.h"

Service::Service() {

}

int Service::countCars(const std::string & manufacturer)
///go through all the cars and count the ones that have a given manufacturer
///returns the number of cars with the given manufacturer or 0 otherwise
{
    int nr = 0;
    std::vector<Car>* c = this->getCars();
    for (auto & it: *c){
        if (it.getManufacturer() == manufacturer)
            nr++;
    }
    return nr;
}

std::vector<Car> *Service::getCarsSorted() {
    std::vector<Car> *allCars = this->getCars();
//    std::sort(allCars->begin(), allCars->end());
    for (auto &i: *allCars){
        for (auto & it: *allCars){
            if (i.getManufacturer() < it.getManufacturer()){
                auto aux = i;
                i = it;
                it = aux;
            }
        }
    }
    return allCars;
}

