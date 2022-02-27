//
// Created by diana on 5/24/2021.
//

#include "Weather.h"
#include <sstream>

Weather::Weather() : startHour(""), endHour(""), precipitation(""), descrpition(""){}

Weather::Weather(const std::string &startHour, const std::string &endHour, const std::string &precipitation,
                 const std::string &descrpition) : startHour(startHour), endHour(endHour), precipitation(precipitation),
                                                   descrpition(descrpition) {}

const std::string &Weather::getStartHour() const {
    return startHour;
}

const std::string &Weather::getEndHour() const {
    return endHour;
}

const std::string &Weather::getPrecipitation() const {
    return precipitation;
}

const std::string &Weather::getDescrpition() const {
    return descrpition;
}

void Weather::setStartHour(const std::string &startHour) {
    Weather::startHour = startHour;
}

void Weather::setEndHour(const std::string &endHour) {
    Weather::endHour = endHour;
}

void Weather::setPrecipitation(const std::string &precipitation) {
    Weather::precipitation = precipitation;
}

void Weather::setDescrpition(const std::string &descrpition) {
    Weather::descrpition = descrpition;
}


std::vector<std::string> tokenize(const std::string &str, char delimiter) {
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string token;
    while (getline(ss, token, delimiter))
        result.push_back(token);
    return result;
}

std::istream &operator >> (std::istream &in, Weather&w) {
    std::string line;
    getline(in, line);
    if (line.empty())
        return in;
    std::vector<std::string> tokens = tokenize(line, ';');
    w.setStartHour(tokens[0]);
    w.setEndHour(tokens[1]);
    w.setPrecipitation(tokens[2]);
    w.setDescrpition(tokens[3]);

    return in;
}