
#include "domain.h"
#include <string>
#include <vector>

Domain::Domain() : date(""), type(""), value(0){}

Domain::Domain(const std::string &date, const std::string &type, const double &value) {
    this->date = date;
    this->type = type;
    this->value = value;
}
