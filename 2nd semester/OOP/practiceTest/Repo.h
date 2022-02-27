#pragma once
#include "Car.h"


class Repo {
private:
    std::vector<Car> *cars;
    void loadFile();

public:
    Repo();
    std::vector<Car>*getRepo(){return cars;}

};

