
#include "gui.h"
#include <QtWidgets/QApplication>
#include <iostream>
#include "Model.h"
#include "Testing.h"
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Testing tests{};
    Repository repository{true};
    Controller controller{ repository };
    //QAbstractItemModel model = nw
    Model model{ controller };
    vector<gui*> windows;
    std::cout<<repository.getWriters().size();
    for (auto writer : repository.getWriters())
        windows.push_back(new gui{ &model,writer });
    for (auto window : windows) { window->show(); std::cout<<223; }

    return a.exec();
}