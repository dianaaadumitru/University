#include "ui.h"
#include <iostream>
#include <string>
#include <iomanip>

using namespace std;


void UI::print_menu() {
    cout<<"\nPossible commands:\n";
    cout<<"1. List genes\n";
    cout<<"2. Add gene\n";
    cout<<"3. Show all the genes that include the sequence entered by the user\n";
    cout<<"0. Exit\n";
}

void UI::display_genes() {
    std::vector<Gene> genes = this->service.sort_genes();
    this->service.sort_genes();
    for (int i = 0; i < genes.size(); ++i) {
        cout<<setw(15)<<left<<genes[i].get_organism()<<" | "<<setw(10)<<left<<genes[i].get_name()<<" | "<<setw(20)<<right<< genes[i].get_sequence()<<"\n";
    }
}

void UI::add_gene_ui() {
    cin.ignore();
    cout << "Enter the organism: ";
    std::string organism;
    getline(cin, organism);

    if (organism == "")
    {
        cout<<"\nInvalid input!\n";
        return;
    }

    cout << "Enter the name: ";
    std::string name;
    getline(cin, name);

    cout<<"Enter the Sequence: ";
    std::string sequence;
    getline(cin, sequence);

    bool ok = this->service.add_gene_service(organism, name, sequence);
    if(ok)
        cout << "\nGene added successfully!\n";
    else
        cout << "\nYou cannot have 2 genes with the same title and presenter!\n";
}

void UI::filter_ui() {
    cin.ignore();
    cout<<"Enter the Sequence: ";
    std::string sequence;
    getline(cin, sequence);
    std::vector<Gene> g = this->service.filter_genes(sequence);
    for (int i = 0; i < g.size(); ++i) {
        cout<<setw(15)<<left<<g[i].get_organism()<<" | "<<setw(10)<<right<<g[i].get_name()<<" | "<< g[i].get_sequence()<<"\n";

    }
}

void UI::run() {
    int done = 1, choice;
    while (done) {
        this->print_menu();
        cout << "\n>>";
        cin >> choice;
        if (choice == 0)
            done = 0;
        else if (choice == 1)
            this->display_genes();
        else if (choice == 2)
            this->add_gene_ui();
        else if (choice == 3)
            this->filter_ui();
        else
            cout << "\nInvalid input!";
    }
}

