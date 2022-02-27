#pragma once
#include "Weather.h"


class Repo {
private:
    std::vector<Weather> *weathers;
    void loadFile();

public:
    Repo();
    std::vector<Weather> *getRepo() { return  weathers; }

};


