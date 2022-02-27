#include "UI.h"
#include <iostream>
#include <string>

using namespace std;

UI::UI(Service *service) {
    this->service = service;
}

UI::~UI() {
    delete service;
}

void UI::print_menu()
{
    cout<<"\nPossible commands:\n";
    cout<<"1. Administrator mode\n";
    cout<<"2. User mode\n";
    cout<<"0. Exit\n";
}

void UI::print_menu_administrator()
{
    cout<<"\nPossible commands:\n";
    cout<<"1. Display tutorials\n";
    cout<<"2. Add tutorial\n";
    cout<<"3. Remove tutorial\n";
    cout<<"4. Update tutorial\n";
    cout<<"0. Back\n";
}

void UI::print_menu_user() {
    cout<<" \nPossible commands:\n";
    cout<<" 10. See tutorials\n";
    cout<<"1. View watchlist\n";
    cout<<"2. Add tutorial to watchlist\n";
    cout<<"3. Delete tutorial from watchlist\n";
    cout<<"4. Next\n";
    cout<<"0. Back\n";
}


void UI::display_tutorials_ui()
{
    std::vector<Tutorial>* v = this->service->get_repo()->get_tutorials();
    if (v->empty())
    {
        cout << "There are no tutorials in the repository." << endl;
        return;
    }

    int i = 1;
    for (auto& it : *v)
    {
        cout << i << ". ";
        this->print_tutorial_ui(it);
        i++;
    }
}

void UI::print_tutorial_ui(const Tutorial& t)
{
    Duration d = t.get_duration();
    cout << "Title: " << t.get_title() << endl;
    cout << "   Presenter: " << t.get_presenter() << endl;
    cout<<  "   Number of likes: "<<t.get_no_of_likes() << endl;
    cout << "   Duration: " << d.getMinutes() << ":" << d.getSeconds() << endl;
    cout << "   Link: "<<t.get_link()<<endl<<endl;
}

void UI::add_tutorial_ui() const
{
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);

    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);

    std::string minutes, seconds;
    cout << "Enter the duration: " << endl;
    cout << "\tMinutes: ";
    getline(cin, minutes);
    cout << "\tSeconds: ";
    getline(cin, seconds);

    std::string likes;
    cout << "Number of likes: ";
    getline(cin, likes);

    std::string link;
    cout << "Link to the tutorial: ";
    getline(cin, link);


    try {
        if (this->service->add_tutorial_service(title, presenter, minutes, seconds, likes, link))
            cout << "\nTutorial added successfully!\n";
    } catch (RepoException& e){
        cout << e.which_message()<<endl;
    }
    catch (ValidationException& e){
        cout << e.which_message()<<endl;
    }
}

void UI::remove_tutorial_ui() const
{
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);
    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);

    try {
        if (this->service->remove_tutorial_service(presenter, title))
            cout<<"\nTutorial removed successfully!\n";
    } catch (RepoException& e){
        cout << e.which_message()<<endl;
    }
    catch (ValidationException& e){
        cout << e.which_message()<<endl;
    }
}

void UI::update_tutorial_ui() const {
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);

    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);

    try {
        this->service->get_repo()->presenter_and_title_empty(presenter, title);
    } catch (RepoException &e){
        cout << e.which_message()<<endl;
        return;
}

    try {
        Tutorial t = this->service->get_repo()->presenter_and_title_exist(presenter, title);
    } catch (RepoException &e){
        cout << e.which_message()<<endl;
        return;
    }

    cout<<"What do you want to update?\n 1. Title\n 2. Presenter\n 3. Duration\n 4. Likes\n 5. Link\n";
    int op;
    cout<<">>";
    cin>>op;
    cin.ignore();
    if (op == 1 || op == 2 || op == 5)
    {
        std::string new_title;
        cout<<"New element: ";
        getline(cin, new_title);

        try {
            if (this->service->update_tutorial_service(presenter, title, op, new_title))
                cout<<"\nTutorial updated successfully!\n";
        } catch (RepoException& e){
            cout << e.which_message()<<endl;
        }

    }
    else if (op == 3)
    {
        std::string minutes, seconds;
        cout << "Enter the duration: " << endl;
        cout << "\tMinutes: ";
        getline(cin, minutes);
        cout << "\tSeconds: ";
        getline(cin, seconds);

        try {
            if (this->service->update_tutorial_duration_service(presenter, title, minutes, seconds))
                cout<<"\nTutorial updated successfully!\n";
        } catch (RepoException& e){
            cout << e.which_message()<<endl;
        }
        catch (ValidationException& e){
            cout << e.which_message()<<endl;
        }
    }
    else if (op == 4)
    {
        std::string likes;
        cout << "Number of likes: ";
        getline(cin, likes);
        try {
            if (this->service->update_tutorial_likes_service(presenter, title, likes))
                cout<<"\nTutorial updated successfully!\n";
        } catch (RepoException& e){
            cout << e.which_message()<<endl;
        }
        catch (ValidationException& e){
            cout << e.which_message()<<endl;
        }
    }
    else
    {
        cout<<"\nInvalid input!\n";
        return;
    }

}

void UI::view_watchlist_ui() {
    std::vector<Tutorial> *array = this->service->get_wathlist()->get_tutorials();

    if (array->empty())
    {
        cout<<"There are no tutorials in watchlist!\n";
        return;
    }
    this->service->get_wathlist()->repoToFile();
    this->service->get_wathlist()->display();
//    int i = 1;
//    for (auto &it: *array)
//    {
//        cout<<i<<". ";
//        this->print_tutorial_ui(it);
//        i++;
//    }
}

void UI::add_tutorial_watchlist_ui() const
{

    int ok = this->service->add_tutorial_watchlist(this->service->get_current_tutorial());
    if(ok)
        cout << "\nTutorial added successfully!\n";
    else
        cout << "\nThis tutorial already is in the watchlist!\n";
}

void UI::delete_tutorial_ui() const {
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);
    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);

    try {
        this->service->get_repo()->presenter_and_title_empty(presenter, title);
    } catch (RepoException &e){
        cout << e.which_message()<<endl;
        return;
    }

    try {
        Tutorial t = this->service->get_repo()->presenter_and_title_exist(presenter, title);
    } catch (RepoException &e){
        cout << e.which_message()<<endl;
        return;
    }

    Tutorial t = this->service->find_by_presenter_and_title_watchlist(presenter, title);
    if (!t.get_presenter().empty() || !t.get_title().empty())
    {
        cout << "\nWould you like to like this video?\n 1. Yes\n 2. No\n >>";
        int op;
        cin>>op;
        if (op == 1) {
            int l = this->service->like_tutorial(presenter, title);
            std::string l2 = to_string(l);
            Tutorial t = this->service->get_repo()->find_by_presenter_and_title(presenter, title);
            this->service->update_tutorial_likes_service(presenter, title, l2);

            cout << "\nYou liked the video!\n";
        }
        else if (op != 1 && op != 2)
            cout<<"\nInvalid input\n";
    }

    bool ok = this->service->delete_tutorial_watchlist(presenter, title);
    if(ok)
        cout<<"\nTutorial removed successfully!\n";
    else
        cout<<"\nTutorial does not exist in the watchlist!\n";
}

void UI::next_ui() {
    this->service->next();
    this->print_tutorial_ui(this->service->get_current_tutorial());
}

void UI::run() {
    int done = 1, c;
    while (done) {
        this->print_menu();
        cout << "\n>>";
        cin >> c;
        if (c == 0) {
            done = 0;
        } else if (c == 1) {
            int choice, d1 = 1;


            while (d1) {
                this->print_menu_administrator();
                cout << "\n>>";
                cin >> choice;
                if (choice == 0) {
                    d1 = 0;

                    //delete []this->service.get_repo().get_tutorials();
                } else if (choice == 1)
                    display_tutorials_ui();
                else if (choice == 2)
                    add_tutorial_ui();
                else if (choice == 3)
                    remove_tutorial_ui();
                else if (choice == 4)
                    update_tutorial_ui();
                else
                    cout << "\nInvalid input!";
            }
        }

        else if (c == 2)
        {
            int d2 = 1;
            while (d2) {
                int choice;
                this->print_menu_user();
                cout << "\n>>";
                cin >> choice;
                if (choice == 0) {
                    d2 = 0;
                }else if (choice == 10){
                    std::string presenter;
                    cout << "\nPresenter: ";
                    cin.ignore();
                    getline(cin, presenter);
                    this->service->create_iterator(presenter);
                    if (!this->service->get_current_tutorial().get_presenter().empty()) {
                        this->print_tutorial_ui(this->service->get_current_tutorial());
                        this->service->start_iteration();
                    } else {
                        cout << "\nThere is no tutorial by that presenter\n";
                        d2 = 0;
                    }
                }

                else if (choice == 1)
                    this->view_watchlist_ui();
                else if (choice == 2)
                    this->add_tutorial_watchlist_ui();
                else if (choice == 3)
                    this->delete_tutorial_ui();
                else if (choice == 4)
                    this->next_ui();
                else
                    cout << "\nInvalid input!\n";
            }
        }

        else
            cout << "\nInvalid command\n";
    }
}
