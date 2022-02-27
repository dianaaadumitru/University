#pragma once
#include "../dynamic_vector/array.h"
#include "../domain/domain.h"

class Repo
{
private:
    DynamicVector<Tutorial> tutorials;
//    DynamicVector tutorials;
public:
    /*
     Constructor, initializes an object of type repo
     */
    Repo() {};
    ~Repo() {};


    /*
     Adds a tutorial to the repo
     returns true if the tutorial was added, false otherwise
     */
    bool add_tutorial(const Tutorial & t);

    /*
     Look after a tutorial by its presenter and title
     Returns the tutorial that was found, or an empty tutorial otherwise
     */
    Tutorial find_by_presenter_and_title(const std::string& presenter, const std:: string& title);

    DynamicVector<Tutorial> get_tutorials() {return tutorials; }
//    DynamicVector get_tutorials() {return tutorials;}

    bool remove_tutorial(const std::string& presenter, const std:: string& title);

    int find_pos_tutorial(const Tutorial& t);

    bool update_tutorial(const std::string& presenter, const std::string& title, int op, const std::string& new_elem);

    bool update_tutorial_duration(const std::string& presenter, const std:: string& title, double min, double sec);

    bool update_tutorial_likes(const std::string& presenter, const std:: string& title, int likes);

};
