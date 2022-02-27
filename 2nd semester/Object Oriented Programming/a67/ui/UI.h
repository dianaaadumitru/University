#pragma once
#include "../service/service.h"

class UI {
public:
    Service *service;
    UI(Service *service);
    void run();
    ~UI();

private:
    static void print_menu();
    static void print_menu_administrator();
    void display_tutorials_ui();
    static void print_tutorial_ui(const Tutorial& t);
    void add_tutorial_ui() const;
    void remove_tutorial_ui() const;
    void update_tutorial_ui() const;
    static void print_menu_user();
    void view_watchlist_ui();
    void delete_tutorial_ui() const;
    void add_tutorial_watchlist_ui() const;
    void next_ui();
};
