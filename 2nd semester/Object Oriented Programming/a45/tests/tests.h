#pragma once
#include "../ui/UI.h"

class Tests
{
private:
    static void test_domain();
    static void test_array();
    static void test_repo();
    static void test_service();
    static void test_iterator();

public:

    Tests() {}
    ~Tests();
    void test_all();
};