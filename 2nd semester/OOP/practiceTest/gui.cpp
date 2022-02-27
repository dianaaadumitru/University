#include <qlayout.h>
#include <qformlayout.h>
#include <QWidget>
#include <qmainwindow.h>
#include <qmessagebox.h>
#include <qlabel.h>
#include "gui.h"


gui::gui(Service *s) : service(s){
    this->initGUI();
    this->populateList();
    QObject::connect(this->showCarsButton, &QPushButton::clicked, this, &gui::countCars);
}

void gui::initGUI() {
    auto* mainLayout = new QHBoxLayout{ this };

    this->carsListWidget = new QListWidget{};
    this->mLineEdit = new QLineEdit{};
    this->resultLineEdit = new QLineEdit{};

    mainLayout->addWidget(carsListWidget);

    auto* carsDetailsLayout = new QFormLayout{};
    carsDetailsLayout->addRow("Manufacturer", this->mLineEdit);
    carsDetailsLayout->addRow("Result", this->resultLineEdit);

    mainLayout->addLayout(carsDetailsLayout);

    this->showCarsButton = new QPushButton{"Show Cars"};
    auto* buttonsLayout = new QGridLayout{};
    buttonsLayout->addWidget(this->showCarsButton, 0, 0);

    mainLayout->addLayout(buttonsLayout);

}

void gui::populateList() {
    this->carsListWidget->clear();
//    std::vector<Car> *allCars = this->service->getCars();
    std::vector<Car> *allCars = this->service->getCarsSorted();
    int i = 0;
    for (Car& c: *allCars){
        this->carsListWidget->addItem(QString::fromStdString(c.getManufacturer()+ "|" + c.getModel() + "|" + c.getYearOfFabrication() +"|" + c.getColour()));
        if (c.getColour() == "blue")
            carsListWidget->item(i)->setForeground(Qt::blue);
        else if (c.getColour() == "red")
            carsListWidget->item(i)->setForeground(Qt::red);
        else if (c.getColour() == "black")
            carsListWidget->item(i)->setForeground(Qt::black);
        else if (c.getColour() == "green")
            carsListWidget->item(i)->setForeground(Qt::green);
        else if (c.getColour() == "yellow")
            carsListWidget->item(i)->setForeground(Qt::darkYellow);
        i++;
    }
}

void gui::countCars() {
    std::string manufacturer = this->mLineEdit->text().toStdString();
    int nr = this->service->countCars(manufacturer);
    this->resultLineEdit->setText(QString::fromStdString(std::to_string(nr)));
}
