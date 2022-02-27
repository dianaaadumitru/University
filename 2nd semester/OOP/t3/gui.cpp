#include <qlayout.h>
#include <qformlayout.h>
#include <QWidget>
#include <qmainwindow.h>
#include <qmessagebox.h>
#include <qlabel.h>
#include <iostream>
#include "gui.h"

gui::gui(Service *s) : service(s){
    this->initGUI();
    this->populateList();
    QObject::connect(this->returnButton,  &QPushButton::clicked, this, &gui::populateList);
//    QObject::connect(this->filterButton,  &QPushButton::clicked, this, &gui::filterCheckBoxes);
    QObject::connect(this->filterButton,  &QPushButton::clicked, this, &gui::filterAll);
}


void gui::initGUI() {
    auto* mainLayout = new QHBoxLayout{ this };
    this->weatherListWidget = new QListWidget{};

    mainLayout->addWidget(this->weatherListWidget);

    this->checkBoxes = new QListWidget{};

    std::vector<std::string> descr = this->service->getAllDescriptions();
    for (int i = 0; i <descr.size(); ++i) {
        QCheckBox* thisLine = new QCheckBox{};
        thisLine->setText(QString(descr[i].c_str()));
        mainLayout->addWidget(thisLine);
        this->checkBoxesList.push_back(thisLine);

    }

    this->returnButton = new QPushButton{"Return to the original list"};
    this->filterButton = new QPushButton{"Filter"};
    auto *buttonsLayout = new QGridLayout{};
    buttonsLayout->addWidget(this->returnButton);
    buttonsLayout->addWidget(this->filterButton);

    mainLayout->addLayout(buttonsLayout);


    this->slider = new QSlider(Qt::Vertical, this);
    slider->setRange(0, 100);
    slider->setValue(100);
//    slider->valueChanged(10);
    mainLayout->addWidget(slider);
}

void gui::populateList() {
    this->weatherListWidget->clear();
    std::vector<Weather> *all = this->service->getWeathers();
    int i = 0;
    for (Weather& w: *all){
        this->weatherListWidget->addItem(QString::fromStdString(w.getStartHour()+ ";" + w.getEndHour() +";" + w.getPrecipitation() + ";" + w.getDescrpition()));
    }
    this->slider->setValue(100);
    for (int i = 0; i < this->checkBoxesList.size(); ++i){
        this->checkBoxesList[i]->setCheckState(Qt::Unchecked);
    }

}

void gui::filterCheckBoxes() {
    std::vector<Weather> *filtered =  new std::vector<Weather>();
    std::vector<Weather> *all = this->service->getWeathers();

    for (int i = 0; i < this->checkBoxesList.size(); ++i) {
        if (this->checkBoxesList[i]->checkState() == Qt::Checked){
            for (auto &w: *all){
                if (w.getDescrpition() == this->checkBoxesList[i]->text().toStdString())
                    filtered->push_back(w);
            }
        }
    }
    this->weatherListWidget->clear();
    for (Weather& w: *filtered){
        this->weatherListWidget->addItem(QString::fromStdString(w.getStartHour()+ ";" + w.getEndHour() +";" + w.getPrecipitation() + ";" + w.getDescrpition()));
    }

}

void gui::filterSlider() {
    int v = this->slider->value();
    std::string value = std::to_string(v);
    std::vector<Weather> *filtered =  new std::vector<Weather>();
    std::vector<Weather> *all = this->service->getWeathers();
    for(Weather& w: *all){
        if(this->service->precipitationSmallerThan(value, w)){
            filtered->push_back(w);
        }
    }
    this->weatherListWidget->clear();
    for (Weather& w: *filtered){
        this->weatherListWidget->addItem(QString::fromStdString(w.getStartHour()+ ";" + w.getEndHour() +";" + w.getPrecipitation() + ";" + w.getDescrpition()));
    }
}

void gui::filterAll() {
    auto *filtered = new std::vector<Weather>();
    std::vector<Weather> *all = this->service->getWeathers();
    int v = this->slider->value();
    std::string value = std::to_string(v);

    for (auto &i : this->checkBoxesList) {
        if (i->checkState() == Qt::Checked) {
            for (auto &w: *all) {
                if (w.getDescrpition() == i->text().toStdString() && std::stoi(w.getPrecipitation()) < v)
                    filtered->push_back(w);
            }
        }
    }
    this->weatherListWidget->clear();
    for (Weather &w: *filtered) {
        this->weatherListWidget->addItem(QString::fromStdString(
                w.getStartHour() + ";" + w.getEndHour() + ";" + w.getPrecipitation() + ";" + w.getDescrpition()));

    }
}
