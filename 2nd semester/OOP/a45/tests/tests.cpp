#include "tests.h"
#include <cassert>

using namespace std;

void Tests::test_all()
{
    this->test_domain();
    this->test_array();
    this->test_repo();
    this->test_service();
    this->test_iterator();
}

void Tests::test_domain()
{
    //test constructer with parameters + getters
    Tutorial t1 {"Pointers", "The Cherno", Duration{16, 58}, 200, "https://www.youtube.com/watch?v=DTxHyVn0ODg"};
    assert(t1.get_title() == "Pointers");
    assert(t1.get_presenter() == "The Cherno");
    assert(t1.get_duration().getMinutes() == 16);
    assert(t1.get_duration().getSeconds() == 58);
    assert(t1.get_no_of_likes() == 200);
    assert(t1.get_link() == "https://www.youtube.com/watch?v=DTxHyVn0ODg");

    //test constructor without parameters + getters
    Tutorial t2{};
    assert(t2.get_title() =="");
    assert(t2.get_presenter() == "");
    assert(t2.get_duration().getSeconds() == 0);
    assert(t2.get_duration().getSeconds() == 0);
    assert(t2.get_link() == "");

//    //test open link
    t1.open_link();

    //test likes
    t1.like();
    assert(t1.get_no_of_likes() == 201);
}

void Tests::test_array()
{
    DynamicVector<int> d {2};

    //test add + getSize
    d = d + 2;
    d = d + 1;
    d.add(10);
    d.add(11);
    d.add(12);
    assert(d.getSize() == 5);

    //test delete + overloading operator []

    d.delete_elem(1);
    d.delete_elem(-1);
    assert(d.getSize() == 4);
    assert(d[1] == 10);
    d.get_all_elems();
    assert(d[0] == 2);

    DynamicVector<int> d2 {d};
    assert(d2[0] == d[0]);

}

void Tests::test_iterator()
{
    Iterator iter;

    assert(iter.get_tutorials_iter().getSize() == 0);
    Iterator();
    assert(iter.get_current() == 0);

    //test add
    Tutorial t1 {"Pointers", "The Cherno", Duration{16, 58}, 200, "https://www.youtube.com/watch?v=DTxHyVn0ODg"};
    Tutorial t2 {"Classes", "Isay Nahor", Duration{8, 41}, 167, "https://www.youtube.com/watch?v=2BP8NhxjrO0"};
    iter.open();
    iter.next();
    iter.add(t1);
    iter.add(t2);
    Tutorial t_test = iter.get_current_tutorial();
    assert(t_test.get_title() == t1.get_title());
    iter.next();
    iter.next();
    Tutorial t_test1 = iter.get_current_tutorial();
    assert(t_test1.get_title() == t1.get_title());


    //test open
    iter.open();

    //test update_likes
    iter.update_likes(0);
    DynamicVector<Tutorial> array = iter.get_tutorials_iter();
    assert(array[0].get_no_of_likes() == 201);

    //test empty_iterator
    iter.iterator_empty();
    assert(iter.get_tutorials_iter().getSize() == 0);
}

void Tests::test_repo()
{
    Repo repo{};
    Tutorial t1 {"Pointers", "The Cherno", Duration{16, 58}, 200, "https://www.youtube.com/watch?v=DTxHyVn0ODg"};
    Tutorial t2 {"Classes", "Isay Nahor", Duration{8, 41}, 167, "https://www.youtube.com/watch?v=2BP8NhxjrO0"};

    //test add
    bool ok = repo.add_tutorial(t1);
    assert(ok == true);
    repo.add_tutorial(t2);
    ok = repo.add_tutorial(t1);
    assert(ok == false);
    assert(repo.get_tutorials().getSize() == 2);

    //test find by presenter and title
    Tutorial t3 = repo.find_by_presenter_and_title("The Cherno", "Pointers");
    assert(t3.get_presenter() == t1.get_presenter());
    Tutorial t4 = repo.find_by_presenter_and_title("Pointers", "a");
    assert(t4.get_presenter() == "");

    //test find pos tutorial
    assert(repo.find_pos_tutorial(t3) == 0);
    assert(repo.find_pos_tutorial(t4) == -1);

    //test remove
    ok = repo.remove_tutorial("The Cherno", "Pointers");
    assert(ok == true);
    assert(repo.get_tutorials().getSize() == 1);
    ok = repo.remove_tutorial("abc", "def");
    assert(ok == false);

    //test update
    repo.add_tutorial(t1);
    ok = repo.update_tutorial("The Cherno", "Pointers", 1, "Classes");
    assert(ok == true);
    Tutorial t5 = repo.find_by_presenter_and_title("The Cherno", "Classes");
    assert(t5.get_title() == "Classes");
    Tutorial t6 {"Pointers", "Isay Nahor", Duration{16, 58}, 200, "https://www.youtube.com/watch?v=DTxHyVn0ODg"};
    repo.add_tutorial(t6);
    repo.update_tutorial("", "Pointers", 1, "Classes");
    ok = repo.update_tutorial("Isay Nahor", "Pointers", 1, "Classes");
    assert(ok == false);

    ok = repo.update_tutorial("The Cherno", "Classes", 2, "Isay Nahor");
    assert(ok == false);
    ok = repo.update_tutorial("The Cherno", "Classes", 2, "abc");
    assert(ok == true);

    ok = repo.update_tutorial("abc", "Classes", 5, "qaz");
    assert(ok == true);

    ok = repo.update_tutorial_duration("abc", "", 10, 23);
    assert(ok == false);
    ok = repo.update_tutorial_duration("abc", "Classes", 10, 23);
    assert(ok == 1);

    ok = repo.update_tutorial_likes("abc", "Classes", 30);
    assert(ok == true);
    ok = repo.update_tutorial_likes("", "Classes", 30);
    assert(ok == false);

}

void Tests::test_service()
{
    Repo repo{};
    Tutorial t1 {"Pointers", "The Cherno", Duration{16, 58}, 200, "https://www.youtube.com/watch?v=DTxHyVn0ODg"};
    Tutorial t2 {"Classes", "Isay Nahor", Duration{8, 41}, 167, "https://www.youtube.com/watch?v=2BP8NhxjrO0"};
    repo.add_tutorial(t1);
    repo.add_tutorial(t2);
    Service service{repo};

    //test get repo
    assert(service.get_repo().get_tutorials().getSize() == repo.get_tutorials().getSize());

    //test add
    bool ok = service.add_tutorial_service("Classes vs Structs", "Caesar Sindy", 8, 35, 150, "https://www.youtube.com/watch?v=fLgTtaqqJp0&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&start_radio=1&t=4");
    assert(ok == true);
    assert(service.get_repo().get_tutorials().getSize() == 3);

    //test remove
    ok = service.remove_tutorial_service("b", "c");
    assert(ok == false);

    //test update tutorial
    ok = service.update_tutorial_service("The Cherno", "Pointers", 1, "Classes");
    assert(ok == 1);
    ok = service.update_tutorial_duration_service("a", "bc", 34, 12);
    assert(ok == false);
    ok = service.update_tutorial_likes_service("The Cherno", "Classes", 700);
    assert(ok == true);

    //test create iterator
    service.create_iterator("");
    assert(service.get_iterator().get_tutorials_iter().getSize() == 3);

    service.create_iterator("The Cherno");
    assert(service.get_iterator().get_tutorials_iter().getSize() == 1);

    //test start iteration
    service.start_iteration();

    //test get current tutorial
    Tutorial tut = service.get_current_tutorial();
    assert(tut.get_presenter() == "The Cherno");

    //test next
    service.next();

    //test add tutorial watchlist
    service.add_tutorial_watchlist(t1);
    assert(service.get_wathlist().get_tutorials().getSize() == 1);

    //test delete tutorial watchlist
    service.delete_tutorial_watchlist(t1.get_presenter(), t1.get_title());
    assert(service.get_wathlist().get_tutorials().getSize() == 0);

    //test find tutorial in watchlist
    service.add_tutorial_watchlist(t1);
    Tutorial t_find = service.find_by_presenter_and_title_watchlist(t1.get_presenter(), t1.get_title());
    assert(t1.get_presenter() == "The Cherno");

    //test like tutorial
    int like = service.like_tutorial(t1.get_presenter(), t1.get_title());
    assert(like == 201);
}


Tests::~Tests() {

}
