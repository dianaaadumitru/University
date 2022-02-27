#include <QWidget>
#include <QListWidget>
#include <qlineedit.h>
#include <qpushbutton.h>
#include <qcheckbox.h>
#include <qslider.h>

#pragma once
#include "Service.h"

class gui : public QWidget {
private:
    Service *service;

    void initGUI();

    void populateList();

    void filterCheckBoxes();

    void filterSlider();

    void filterAll();

    QListWidget* weatherListWidget, *checkBoxes;
    QCheckBox* weather1;
    QPushButton *returnButton, *filterButton;

    QSlider*slider;

    std::vector<QCheckBox* > checkBoxesList;


public:
    explicit gui(Service *s);


};