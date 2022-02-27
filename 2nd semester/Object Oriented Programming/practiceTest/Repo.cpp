//
// Created by diana on 5/23/2021.
//

#include "Repo.h"
#include <fstream>
#include <iostream>
#include <algorithm>

Repo::Repo() {
    this->cars = new std::vector<Car>();
    this->loadFile();
}

void Repo::loadFile() {
    std::ifstream infile("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\practiceTest\\car.txt");
    if (!infile.is_open()) {
        perror("Cannot open file: ");

    }
    Car c;
    while (infile >> c){
        cars->push_back(c);
    }
    infile.close();
}

