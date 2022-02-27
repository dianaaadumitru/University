#pragma once
#include "Repo.h"


class Service {
private:
    Repo *repo;
public:
    Service();
    Repo* getRepo() { return repo; }
    Service(Repo *repo) { this->repo = repo; }
    std::vector<Weather> *getWeathers(){ return repo->getRepo(); }
    std::vector<std::string> getAllDescriptions();
    int checkIfDescriptionExists(std::vector<std::string> des, std::string newD);
    int precipitationSmallerThan(std::string& value, Weather w);
};

