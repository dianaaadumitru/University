#include <iostream>
#include <QTWidgets/QApplication>
#include "gui.h"
//#include "Service.h"

int main(int argc, char* argv[]){
    QApplication a(argc, argv);
    Repo *repo = new Repo();
    Service *service = new Service(repo);
    gui gui{service};
    gui.show();
    return a.exec();;
}
