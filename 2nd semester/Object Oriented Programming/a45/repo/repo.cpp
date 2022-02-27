
#include "repo.h"

bool Repo::add_tutorial(const Tutorial & t)
///add a new tutorial to the list
///returns true if the tutorial was added, false otherwise
{
    Tutorial tutorial = find_by_presenter_and_title(t.get_presenter(), t.get_title());
    if (tutorial.get_presenter() != "" || tutorial.get_title() != "")
        return false;
    this->tutorials.add(t);
    return true;
}

Tutorial Repo::find_by_presenter_and_title(const std::string& presenter, const std:: string& title)
///search in the list of tutorials for a tutorial by its title and presenter
///returns the tutorial if it exists or an empty tutorial otherwise
{
    Tutorial* tutorials_from_array = this->tutorials.get_all_elems();

    for(int i = 0; i < this->tutorials.getSize(); i++)
    {
        Tutorial s = tutorials_from_array[i];
        if(s.get_presenter() == presenter && s.get_title() == title)
            return s;
    }
    return Tutorial{};
}

int Repo::find_pos_tutorial(const Tutorial& t)
///search in the list of tutorials for a given tutorial
///returns the index of the tutorial if it exists or an empty tutorial otherwise
{
    for (int i = 0; i < this->tutorials.getSize(); ++i) {
        if (this->tutorials[i].get_title() == t.get_title() && this->tutorials[i].get_presenter() == t.get_presenter())
            return i;
    }
    return -1;
}

bool Repo::remove_tutorial(const std::string& presenter, const std:: string& title)
///updates the title the presenter or the link of a tutorial, depending on the user option
///returns true if the tutorial was updated, false otherwise
{
    Tutorial t = this->find_by_presenter_and_title(presenter, title);
    if (t.get_presenter() == "" && t.get_title() == "")
        return false;

    int pos = this->find_pos_tutorial(t);
    this->tutorials.delete_elem(pos);
    return true;
}

bool Repo::update_tutorial(const std::string& presenter, const std:: string& title, int op, const std::string& new_elem)
///updates the title the presenter or the link of a tutorial, depending on the user option
///returns true if the tutorial was updated, false otherwise
{
    Tutorial t = this->find_by_presenter_and_title(presenter, title);
    bool ok;
    if (t.get_presenter() == "" && t.get_title() == "")
        return false;
    if (op == 1)
    {
        Tutorial newt {new_elem, t.get_presenter(), t.get_duration(), t.get_no_of_likes(), t.get_link()};
        ok = this->add_tutorial(newt);
        if (!ok)
            return false;
        this->remove_tutorial(presenter, title);
    }
    else if (op == 2)
    {
        Tutorial newt {t.get_title(), new_elem, t.get_duration(), t.get_no_of_likes(), t.get_link()};
        ok = this->add_tutorial(newt);
        if (!ok)
            return false;
        this->remove_tutorial(presenter, title);
    }
    else
    {
        this->remove_tutorial(presenter, title);
        Tutorial newt {t.get_title(), t.get_presenter(), t.get_duration(), t.get_no_of_likes(), new_elem};
        this->add_tutorial(newt);
    }
    return true;
}

bool Repo::update_tutorial_duration(const std::string& presenter, const std:: string& title, double min, double sec)
///updates the title the duration of a tutorial
///returns true if the tutorial was updated, false otherwise
{
    Tutorial t = this->find_by_presenter_and_title(presenter, title);
    if (t.get_presenter() == "" && t.get_title() == "")
        return false;
    Duration d {min, sec};
    this->remove_tutorial(presenter, title);
    Tutorial newt {t.get_title(), t.get_presenter(), d, t.get_no_of_likes(), t.get_link()};
    this->add_tutorial(newt);
    return true;
}

bool Repo::update_tutorial_likes(const std::string& presenter, const std:: string& title, int likes)
///updates the title the likes of a tutorial
///returns true if the tutorial was updated, false otherwise
{
    Tutorial t = this->find_by_presenter_and_title(presenter, title);
    if (t.get_presenter() == "" && t.get_title() == "")
        return false;
    this->remove_tutorial(presenter, title);
    Tutorial newt {t.get_title(), t.get_presenter(), t.get_duration(), likes, t.get_link()};
    this->add_tutorial(newt);
    return true;
}