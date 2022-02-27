#pragma once
#include <string>


class Domain {
private:
    std::string date;
    std::string type;
    double value;
public:
    Domain();
    Domain(const std::string& date, const std::string& type, const double& value);

};

