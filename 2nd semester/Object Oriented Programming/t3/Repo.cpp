#include "Repo.h"
#include <fstream>

Repo::Repo() {
    this->weathers = new std::vector<Weather>();
    this->loadFile();
}

void Repo::loadFile() {
    std::ifstream infile("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\t3-913-Dumitru-Diana\\weather.txt");
    if (!infile.is_open()) {
        perror("Cannot open file: ");

    }
    Weather w;
    while (infile >> w){
        weathers->push_back(w);
    }
    infile.close();
}
