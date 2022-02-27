//
// Created by diana on 4/26/2021.
//

#include "UI.h"
#include <iostream>
#include <string>

using namespace std;

UI::UI(Person *domain) {
    this->domain = domain;
}

void UI::printMenu() {
    cout<<"\nPossible commands:\n";
    cout<<"1. Display\n";
    cout<<"2. Add\n";
    cout<<"0. Exit\n";
}

void UI::display() {
    std::vector<Domain>* m = domain->getAllMeasurements();
    for (int i=0; i<m->size(); i++){
//        cout<<m[i].toString()<<endl;
    }
}

void UI::add() {
    cin.ignore();
    cout<<"Enter a date: ";
    std::string date;
    getline(cin, date);
    if (date.size() != 10)
    {
        throw ValidationException("not a valid data");
    }
    cout<<"Enter a type: ";
    std::string type;
    getline(cin, type);
    if (type== "BP"){
        int value;
//        Measurement();
//        this->person->addMeasurement(m);
    }
}

void UI::run() {
    int done = 1, c;
    while (done) {
        this->printMenu();
        cout << "\n>>";
        cin >> c;
        if (c == 0) {
            done = 0;}
        else if (c == 1){
            this->display();
        }
        else if(c == 2){
            this->add();
        }
    }
}
