#include "UI.h"
#include <iostream>
#include <string>

using namespace std;


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
    DynamicVector<Tutorial> array = this->service.get_repo().get_tutorials();
    Tutorial* tutorials = array.get_all_elems();
    if (array.getSize() == 0)
    {
        cout<<"There are no tutorials available!\n";
        return;
    }
    for (int i = 0; i < array.getSize(); ++i) {
        cout<<i + 1<<". ";
        Tutorial t = tutorials[i];
        this->print_tutorial_ui(t);
    }
}
//void UI::display_tutorials_ui() {
//    DynamicVector array = this->service.get_repo().get_tutorials();
//    Tutorial* tutorials = array.get_all_elems();
//    if (array.getSize() == 0)
//    {
//        cout<<"There are no tutorials available!\n";
//        return;
//    }

//    for (int i = 0; i < array.getSize(); ++i) {
//        cout<<i + 1<<". ";
//        Tutorial t = tutorials[i];
//        this->print_tutorial_ui(t);
//    }
//}

void UI::print_tutorial_ui(const Tutorial& t)
{
    Duration d = t.get_duration();
    cout << "Title: " << t.get_title() << endl;
    cout << "   Presenter: " << t.get_presenter() << endl;
    cout<<  "   Number of likes: "<<t.get_no_of_likes() << endl;
    cout << "   Duration: " << d.getMinutes() << ":" << d.getSeconds() << endl;
    cout << "   Link: "<<t.get_link()<<endl<<endl;
}

void UI::add_tutorial_ui()
{
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);
    if (title == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);
    if (presenter == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    double minutes, seconds;
    cout << "Enter the duration: " << endl;
    cout << "\tMinutes: ";
    cin >> minutes;
    if (minutes < 0)
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cin.ignore();
    cout << "\tSeconds: ";
    cin >> seconds;
    if (seconds < 0 || seconds >59)
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cin.ignore();
    int likes;
    cout << "Number of likes: ";
    cin >> likes;
    if (likes < 0)
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cin.ignore();
    cout << "Link to the tutorial: ";
    std::string link;
    getline(cin, link);
    if (link == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    int ok = this->service.add_tutorial_service(title, presenter, minutes, seconds, likes, link);
    if(ok)
        cout << "\nTutorial added successfully!\n";
    else
        cout << "\nYou cannot have 2 offers with the same title and presenter!\n";
}

void UI::remove_tutorial_ui()
{
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);
    if (title == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);
    if (presenter == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }

    bool ok = this->service.remove_tutorial_service(presenter, title);
    if(ok)
        cout<<"\nTutorial removed successfully!\n";
    else
        cout<<"\nTutorial does not exist!\n";
}

void UI::update_tutorial_ui()
{
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);
    if (title == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);
    if (presenter == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    Tutorial t = this->service.get_repo().find_by_presenter_and_title(presenter, title);
    if (t.get_presenter() == "" && t.get_title() == "")
    {
        cout<<"\nThis tutorial does not exist!\n";
        return;
    }
    cout<<"What do you want to update?\n 1. Title\n 2. Presenter\n 3. Duration\n 4. Likes\n 5. Link\n";
    int op;
    bool ok;
    cout<<">>";
    cin>>op;
    cin.ignore();
    if (op == 1 || op == 2 || op == 5)
    {
        std::string new_title;
        cout<<"New element: ";
        getline(cin, new_title);
        if (new_title == "")
        {
            cout<<"\nInvalid input\n";
            return;
        }
        ok = this->service.update_tutorial_service(presenter, title, op, new_title);
    }
    else if (op == 3)
    {
        double minutes, seconds;
        cout << "Enter the duration: " << endl;
        cout << "\tMinutes: ";
        cin >> minutes;
        if (minutes < 0)
        {
            cout<<"\nInvalid input!\n";
            return;
        }
        cin.ignore();
        cout << "\tSeconds: ";
        cin >> seconds;
        if (seconds < 0 || seconds >59)
        {
            cout<<"\nInvalid input!\n";
            return;
        }
        ok = this->service.update_tutorial_duration_service(presenter, title, minutes, seconds);
    }
    else if (op == 4)
    {
        int likes;
        cout << "Number of likes: ";
        cin >> likes;
        if (likes < 0)
        {
            cout<<"\nInvalid input!\n";
            return;
        }
        ok = this->service.update_tutorial_likes_service(presenter, title, likes);
    }
    else
    {
        cout<<"\nInvalid input!\n";
        return;
    }

    if (ok)
        cout<<"\nUpdated successful!\n";
    else
        cout << "\nYou cannot have 2 offers with the same title and presenter!\n";
}

void UI::view_watchlist_ui() {
    DynamicVector<Tutorial> array = this->service.get_wathlist().get_tutorials();
    Tutorial* tutorials = array.get_all_elems();
    if (array.getSize() == 0)
    {
        cout<<"There are no tutorials in watchlist!\n";
        return;
    }
    for (int i = 0; i < array.getSize(); ++i) {
        cout<<i + 1<<". ";
        Tutorial t = tutorials[i];
        this->print_tutorial_ui(t);
    }
}

void UI::add_tutorial_watchlist_ui()
{

    int ok = this->service.add_tutorial_watchlist(this->service.get_current_tutorial());
    if(ok)
        cout << "\nTutorial added successfully!\n";
    else
        cout << "\nThis tutorial already is in the watchlist!\n";
}

void UI::delete_tutorial_ui() {
    cin.ignore();
    cout << "Enter the title: ";
    std::string title;
    getline(cin, title);
    if (title == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }
    cout << "Enter the presenter: ";
    std::string presenter;
    getline(cin, presenter);
    if (presenter == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }

    Tutorial t = this->service.find_by_presenter_and_title_watchlist(presenter, title);
    if (t.get_presenter() != "" || t.get_title() != "")
    {
        cout << "\nWould you like to like this video?\n 1. Yes\n 2. No\n >>";
        int op;
        cin>>op;
        if (op == 1) {
            int l = this->service.like_tutorial(presenter, title);
            Tutorial t = this->service.get_repo().find_by_presenter_and_title(presenter, title);
            this->service.update_tutorial_likes_service(presenter, title, l);

            cout << "\nYou liked the video!\n";
        }
        else if (op != 1 && op != 2)
            cout<<"\nInvalid input\n";
    }

    bool ok = this->service.delete_tutorial_watchlist(presenter, title);
    if(ok)
        cout<<"\nTutorial removed successfully!\n";
    else
        cout<<"\nTutorial does not exist in the watchlist!\n";
}

void UI::next_ui() {
    this->service.next();
    this->print_tutorial_ui(this->service.get_current_tutorial());
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
                    this->service.create_iterator(presenter);
                    if (this->service.get_current_tutorial().get_presenter() != "") {
                        this->print_tutorial_ui(this->service.get_current_tutorial());
                        this->service.start_iteration();
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
