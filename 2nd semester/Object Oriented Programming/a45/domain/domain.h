#pragma once
#include <iostream>

class Duration
{
private:
    double minutes, seconds;

public:
    Duration() : minutes(0), seconds(0) {};

    //constructor
    Duration(double min, double sec): minutes(min), seconds(sec) {};

    //getters and setters

    double getMinutes() const { return minutes; }
    double getSeconds() const { return seconds; }
    void setMinutes(double min) { minutes = min; }
    void setSeconds(double sec) { seconds = sec; }

};

class Tutorial
{
private:
    /*
     * UNIQUELY IDENTIFIED BY THEIR PRESENTER AND TITLE
     */
    std::string title;
    std::string presenter;
    Duration duration;
    int likes;
    std::string link;
public:
    // default constructor for a playlist
    Tutorial();
//    ~Tutorial();

    // constructor with parameters
    Tutorial(const std::string& title, const std::string& presenter, const Duration& duration, const int& likes, const std::string& link);

    //getters and setters
    std::string get_title() const;
    std::string get_presenter() const;
    int get_no_of_likes() const;
    std::string get_link() const;
    Duration get_duration() const;
    void set_title(std::string& value){title = value;}

    void open_link() const;
    void like();


};