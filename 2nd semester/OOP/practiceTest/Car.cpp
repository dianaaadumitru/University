//
// Created by diana on 5/23/2021.
//

#include "Car.h"
#include <sstream>
#include <iostream>

Car::Car(const std::string &manufacturer, const std::string &model, const std::string &yearOfFabrication,
         const std::string &colour) : manufacturer(manufacturer), model(model), yearOfFabrication(yearOfFabrication),
                                      colour(colour) {}

const std::string &Car::getManufacturer() const {
    return manufacturer;
}

const std::string &Car::getModel() const {
    return model;
}

const std::string &Car::getYearOfFabrication() const {
    return yearOfFabrication;
}

const std::string &Car::getColour() const {
    return colour;
}

Car::Car() : manufacturer(""), model(""), yearOfFabrication(""), colour("") {}

std::vector<std::string> tokenize(const std::string &str, char delimiter) {
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string token;
    while (getline(ss, token, delimiter))
        result.push_back(token);
    return result;
}

std::istream &operator >> (std::istream &in, Car &c) {
    std::string line;
    getline(in, line);
    if (line.empty())
        return in;
    std::vector<std::string> tokens = tokenize(line, ',');
//    if (tokens.size() != 4)
    c.setManufacturer(tokens[0]);
    c.setModel(tokens[1]);
    c.setYearOfFabrication(tokens[2]);
    c.setColour(tokens[3]);
//    c =  new Car(tokens[0], tokens[1], tokens[2], tokens[3]);
    return in;
}

void Car::setManufacturer(const std::string &manufacturer) {
    Car::manufacturer = manufacturer;
}

void Car::setModel(const std::string &model) {
    Car::model = model;
}

void Car::setYearOfFabrication(const std::string &yearOfFabrication) {
    Car::yearOfFabrication = yearOfFabrication;
}

void Car::setColour(const std::string &colour) {
    Car::colour = colour;
}
