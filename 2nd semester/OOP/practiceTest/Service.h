#pragma once
#include "Repo.h"

class Service {
private:
    Repo *repo;
public:
    Service();
    Repo* getRepo() { return repo; }
    Service(Repo *repo) { this->repo = repo; }
    std::vector<Car> *getCars() { return repo->getRepo(); }

    std::vector<Car>*getCarsSorted();

    int countCars(const std::string & manufacturer);
};
