#pragma once
#include "service.h"

class UI
{
public:
    Service service;
    void run();

private:
    void print_menu();
    void display_genes();
    void add_gene_ui();
    void filter_ui();
};
