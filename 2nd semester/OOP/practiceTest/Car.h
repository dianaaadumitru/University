#pragma once
#include <string>
#include <vector>

class Car {
private:
    std::string manufacturer;
    std::string model;
    std::string yearOfFabrication;
    std::string colour;
public:
    Car(const std::string &manufacturer, const std::string &model, const std::string &yearOfFabrication,
        const std::string &colour);

    const std::string &getManufacturer() const;

    const std::string &getModel() const;

    Car();

    const std::string &getYearOfFabrication() const;

    const std::string &getColour() const;

    friend std::istream &operator >> (std::istream &in, Car &c);

    void setManufacturer(const std::string &manufacturer);

    void setModel(const std::string &model);

    void setYearOfFabrication(const std::string &yearOfFabrication);

    void setColour(const std::string &colour);

};

std::vector<std::string> tokenize(const std::string& str, char delimiter);

