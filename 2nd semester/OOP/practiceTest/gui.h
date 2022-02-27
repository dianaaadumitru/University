#include <QWidget>
#include <QListWidget>
#include <qlineedit.h>
#include <qpushbutton.h>

#pragma once
#include "Service.h"

class gui : public QWidget {
private:
    Service* service;
    void initGUI();
    void populateList();

    //graphical elements
    QListWidget* carsListWidget;
    QLineEdit* mLineEdit, *resultLineEdit;
    QPushButton* showCarsButton;

    void countCars();


public:
    explicit gui(Service *s);

//    ~gui() override;

private:
};