#pragma once
#include <string>
#include <vector>


class Weather {
private:
    std::string startHour;
    std::string endHour;
    std::string precipitation;
    std::string descrpition;

public:
    Weather();

    Weather(const std::string &startHour, const std::string &endHour, const std::string &precipitation,
            const std::string &descrpition);

    const std::string &getStartHour() const;

    const std::string &getEndHour() const;

    const std::string &getPrecipitation() const;

    const std::string &getDescrpition() const;

    void setStartHour(const std::string &startHour);

    void setEndHour(const std::string &endHour);

    void setPrecipitation(const std::string &precipitation);

    void setDescrpition(const std::string &descrpition);

    friend std::istream &operator >> (std::istream &in, Weather &w);
};

std::vector<std::string> tokenize(const std::string& str, char delimiter);


