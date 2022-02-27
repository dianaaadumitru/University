#include "domain.h"
#include <windows.h>
#include <shellapi.h>


Tutorial::Tutorial() : title(""), presenter(""), duration(Duration()), likes(0), link("") {}

Tutorial::Tutorial(const std::string& title, const std::string& presenter, const Duration& duration, const int& likes, const std::string& link)
{
    this->title = title;
    this->presenter = presenter;
    this->duration = duration;
    this->likes = likes;
    this->link = link;
}

std::string Tutorial::get_title() const
{
    return this->title;
}

std::string Tutorial::get_presenter() const
{
    return this->presenter;
}

Duration Tutorial::get_duration() const
{
    return this->duration;
}

int Tutorial::get_no_of_likes() const
{
    return this->likes;
}

std::string Tutorial::get_link() const
{
    return this->link;
}

void Tutorial::open_link() const
{
    ShellExecuteA(NULL, NULL, "chrome.exe", this->get_link().c_str(), NULL, SW_SHOWMAXIMIZED);
}

void Tutorial::like()
{
    this->likes++;
}


