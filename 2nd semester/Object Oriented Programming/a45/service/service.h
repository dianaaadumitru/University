#pragma once
#include "../repo/repo.h"
#include "../iterator/iterator.h"

class Service
{
private:
    Repo repo;
    Repo watchlist;
    Iterator iter;

public:
    Service(const Repo &r): repo{r} {}

    //administrator mode functionalities
    Repo get_repo() const { return repo; }

    bool add_tutorial_service(const std::string& title, const std::string& presenter, double min, double sec, int likes, const std::string& link);

    bool remove_tutorial_service(const std::string& presenter, const std:: string& title);

    bool update_tutorial_service(const std::string& presenter, const std:: string& title, int op, const std::string& new_elem);

    bool update_tutorial_duration_service(const std::string& presenter, const std:: string& title, double min, double sec);

    bool update_tutorial_likes_service(const std::string& presenter, const std:: string& title, int likes);

    //user mode functionalities
    Repo get_wathlist() const {return watchlist; }

    void create_iterator(const std::string &presenter);

    void start_iteration();

    Tutorial get_current_tutorial();

    void next();

    bool add_tutorial_watchlist(const Tutorial & t);

    bool delete_tutorial_watchlist(const std::string& presenter, const std:: string& title);

    Tutorial find_by_presenter_and_title_watchlist(const std::string& presenter, const std:: string& title);

    int like_tutorial(const std::string& presenter, const std:: string& title);

    Iterator get_iterator() {return iter; }
};
