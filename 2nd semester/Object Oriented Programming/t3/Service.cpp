#include "Service.h"

Service::Service() {

}

std::vector<std::string> Service::getAllDescriptions() {
    std::vector<std::string> des;
    std::vector<Weather> *all = this->getWeathers();
    for (auto & w: *all) {
        if (this->checkIfDescriptionExists(des, w.getDescrpition())){
            des.push_back(w.getDescrpition());
        }
    }
    return des;
}

int Service::checkIfDescriptionExists(std::vector<std::string> des, std::string newD) {
    for (int i = 0; i < des.size(); ++i) {
        if (des[i] == newD)
            return 0;
    }
    return 1;
}

int Service::precipitationSmallerThan(std::string& value, Weather w) {
    if(w.getPrecipitation() < value)
        return 1;
    return 0;
}
