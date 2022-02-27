//
// Created by diana on 4/26/2021.
//
#include "UI.h"

using namespace std;

int main(){
    Person *person{};
    auto *ui = new UI(person);
    ui->run();
    return 0;

}

