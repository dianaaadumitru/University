#pragma once

//#include "../dynamic_vector/array.h"
#include "../domain/domain.h"
#include <vector>


class Repo
{
private:
   std::vector<Tutorial>* tutorials;

public:
    /*
     Constructor, initializes an object of type repo
     */
    Repo();
    ~Repo();
    Repo(const Repo& r);
    Repo& operator=(const Repo& t);

    bool add_tutorial(const Tutorial & t);

    Tutorial find_by_presenter_and_title(const std::string& presenter, const std:: string& title);

    std::vector<Tutorial>* get_tutorials() {return tutorials; }

    bool remove_tutorial(const std::string& presenter, const std:: string& title);

    int find_pos_tutorial(const Tutorial& t);

    bool update_tutorial(const std::string& presenter, const std::string& title, int op, const std::string& new_elem);

    bool update_tutorial_duration(const std::string& presenter, const std::string& title, const std::string& min, const std::string& sec);

    bool update_tutorial_likes(const std::string& presenter, const std:: string& title, const std:: string& likes);

    static bool presenter_and_title_empty(const std::string& presenter, const std:: string& title);

    Tutorial presenter_and_title_exist(const std::string& presenter, const std:: string& title);
};

class RepoException : public ValidationException {
public:
    RepoException(std::string message): ValidationException(message){}
};
