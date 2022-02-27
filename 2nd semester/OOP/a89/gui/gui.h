#pragma once
#include <QWidget>
#include "../service/service.h"
#include <qlistwidget.h>
#include <qwidget.h>
#include <qlineedit.h>
#include <qpushbutton.h>

//QT_BEGIN_NAMESPACE
//namespace Ui { class GUI; }
//QT_END_NAMESPACE

class GUI : public QWidget {
private:
    Service* serv;
    void initGUI();
    void initGUIAdmin();
    void populateListAdmin();
    void connectSignalsAndSlotsAdmin();
    int getSelectedIndexAdmin();
    void addTutorialAdmin();
    void removeTutorialAdmin();
    void updateTutorialAdmin();

    void initGUIUser();
    void populateListUser();

    //graphical elements
    QListWidget* tutorialsListWidget, *watchlist;
    QLineEdit* presenterLineEdit, *titleLineEdit, *minLineEdit, *secLineEdit, *likesLineEdit, *linkLineEdit, *searchLineEdit;
    QPushButton *addButton, *deleteButton, *updateButton, *adminButton, *userButton, *viewWatchlistButton, *addWatchlistButton, *deleteWatchlistButton, *nextWatchlistButton, *changeModeButton, *search;


public:
    GUI(Service* s);
};

