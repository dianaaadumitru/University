#include "gui.h"
#include <qlayout.h>
#include <qformlayout.h>
#include <QWidget>
#include <qmainwindow.h>
#include <qmessagebox.h>
#include <qlabel.h>

GUI::GUI(Service *s) : serv{ s } {
//    QMainWindow::showMaximized();
//    this->initGUIAdmin();
//    this->populateListAdmin();
//    this->connectSignalsAndSlotsAdmin();
    this->initGUI();
//    this->initGUIUser();
}

void GUI::initGUI() {
    auto* mainLayout = new QHBoxLayout{ this };

    auto* leftW = new QWidget{};
    auto* leftSide = new QVBoxLayout{ leftW };

    this->tutorialsListWidget = new QListWidget{};

    this->presenterLineEdit = new QLineEdit{};
    this->titleLineEdit = new QLineEdit{};
    this->minLineEdit = new QLineEdit{};
    this->secLineEdit = new QLineEdit{};
    this->likesLineEdit = new QLineEdit{};
    this->linkLineEdit = new QLineEdit{};
    this->searchLineEdit = new QLineEdit{};

    this->addButton = new QPushButton{"Add"};
    this->deleteButton = new QPushButton{"Delete"};
    this->updateButton = new QPushButton{"Update"};
    this->changeModeButton = new QPushButton{"Change mode"};

    QWidget* tutorialDataWidget = new QWidget{};
    auto* tutorialDetailsLayout = new QFormLayout{ tutorialDataWidget };

    tutorialDetailsLayout->addRow("Presenter", this->presenterLineEdit);
    tutorialDetailsLayout->addRow("Title", this->titleLineEdit);
    tutorialDetailsLayout->addRow("Minutes", this->minLineEdit);
    tutorialDetailsLayout->addRow("Seconds", this->secLineEdit);
    tutorialDetailsLayout->addRow("Likes", this->likesLineEdit);
    tutorialDetailsLayout->addRow("Link", this->linkLineEdit);

    auto *buttonsWidget = new QWidget{};
    auto* buttonsLayout = new QGridLayout{buttonsWidget};
    buttonsLayout->addWidget(this->addButton, 0, 0);
    buttonsLayout->addWidget(this->deleteButton, 0, 1);
    buttonsLayout->addWidget(this->updateButton, 1, 0);
    buttonsLayout->addWidget(this->changeModeButton, 1, 1);

    leftSide->addWidget(new QLabel{ "Tutorials" });
    leftSide->addWidget(this->tutorialsListWidget);
    leftSide->addWidget(tutorialDataWidget);
    leftSide->addWidget(buttonsWidget);

    mainLayout->addWidget(leftW);

    this->populateListAdmin();
    this->connectSignalsAndSlotsAdmin();

    QWidget* rightW = new QWidget{};
    QVBoxLayout* rightSide = new QVBoxLayout{ rightW };

    this->watchlist = new QListWidget{};

    QWidget* watchlistDataWidget = new QWidget{};
    auto* watchlistDetailsLayout = new QFormLayout{ tutorialDataWidget };

    this->addWatchlistButton = new QPushButton{"Add to watchlist"};
    this->viewWatchlistButton = new QPushButton{"View watchlist"};
    this->deleteWatchlistButton = new QPushButton{"Delete from watchlist"};
    this->nextWatchlistButton = new QPushButton{"Next"};
    this->search = new QPushButton{"Search"};

    QWidget* watchlistButtonsWidget = new QWidget{};
    QHBoxLayout* buttonsLayoutWatchlist = new QHBoxLayout{ watchlistButtonsWidget };
    watchlistDetailsLayout->addRow("Presenter", this->searchLineEdit);
//    watchlistDetailsLayout->addRow("Presenter", this->presenterLineEdit);

    buttonsLayoutWatchlist->addWidget(this->viewWatchlistButton);
    buttonsLayoutWatchlist->addWidget(this->addWatchlistButton);
    buttonsLayoutWatchlist->addWidget(this->deleteWatchlistButton);
    buttonsLayoutWatchlist->addWidget(this->nextWatchlistButton);
    buttonsLayoutWatchlist->addWidget(this->search);

    rightSide->addWidget(new QLabel{"Watchlist" });
    rightSide->addWidget(this->watchlist);
    rightSide->addWidget(watchlistDataWidget);
//    rightSide->addWidget(watchlistButtonsWidget);
    rightSide->addWidget(watchlistButtonsWidget);

    mainLayout->addWidget(rightW);
}

void GUI::initGUIAdmin() {
    this->tutorialsListWidget = new QListWidget{};

    this->presenterLineEdit = new QLineEdit{};
    this->titleLineEdit = new QLineEdit{};
    this->minLineEdit = new QLineEdit{};
    this->secLineEdit = new QLineEdit{};
    this->likesLineEdit = new QLineEdit{};
    this->linkLineEdit = new QLineEdit{};

    this->addButton = new QPushButton{"Add"};
    this->deleteButton = new QPushButton{"Delete"};
    this->updateButton = new QPushButton{"Update"};

    auto* mainLayout = new QVBoxLayout{ this };

    mainLayout->addWidget(this->tutorialsListWidget);

    auto* tutorialDetailsLayout = new QFormLayout{};

    tutorialDetailsLayout->addRow("Presenter", this->presenterLineEdit);
    tutorialDetailsLayout->addRow("Title", this->titleLineEdit);
    tutorialDetailsLayout->addRow("Minutes", this->minLineEdit);
    tutorialDetailsLayout->addRow("Seconds", this->secLineEdit);
    tutorialDetailsLayout->addRow("Likes", this->likesLineEdit);
    tutorialDetailsLayout->addRow("Link", this->linkLineEdit);

    mainLayout->addLayout(tutorialDetailsLayout);

    auto* buttonsLayout = new QGridLayout{};
    buttonsLayout->addWidget(this->addButton, 0, 0);
    buttonsLayout->addWidget(this->deleteButton, 0, 1);
    buttonsLayout->addWidget(this->updateButton, 0, 2);

    mainLayout->addLayout(buttonsLayout);
}

void GUI::populateListAdmin() {
    this->tutorialsListWidget->clear();

    std::vector<Tutorial> *allTutorials = this->serv->get_repo()->get_tutorials();
    for (Tutorial& t: *allTutorials){
        this->tutorialsListWidget->addItem(QString::fromStdString(t.get_presenter() + ", " + t.get_title() + ", " + std::to_string(t.get_duration().getMinutes()) + ":" + std::to_string(t.get_duration().getSeconds()) + ", " + std::to_string(t.get_no_of_likes()) + ", " + t.get_link()));
    }
}

void GUI::connectSignalsAndSlotsAdmin() {
    QObject::connect(this->tutorialsListWidget, &QListWidget::itemSelectionChanged, [this](){
        int selectedIndex = this->getSelectedIndexAdmin();

        if (selectedIndex < 0)
            return;
        std::vector<Tutorial> *allTutorials = this->serv->get_repo()->get_tutorials();
        Tutorial t;
        int i = 0;
        for (auto& it : *allTutorials)
        {
            if (i == selectedIndex){
                t = it;
                break;
            }
            i++;
        }
        this->presenterLineEdit->setText(QString::fromStdString(t.get_presenter()));
        this->titleLineEdit->setText(QString::fromStdString(t.get_title()));
        this->minLineEdit->setText(QString::fromStdString(std::to_string(t.get_duration().getMinutes())));
        this->secLineEdit->setText(QString::fromStdString(std::to_string(t.get_duration().getSeconds())));
        this->likesLineEdit->setText(QString::fromStdString(std::to_string(t.get_no_of_likes())));
        this->linkLineEdit->setText(QString::fromStdString(t.get_link()));
    });

    QObject::connect(this->addButton, &QPushButton::clicked, this, &GUI::addTutorialAdmin);
    QObject::connect(this->deleteButton, &QPushButton::clicked, this, &GUI::removeTutorialAdmin);
    QObject::connect(this->updateButton, &QPushButton::clicked, this, &GUI::updateTutorialAdmin);
}

int GUI::getSelectedIndexAdmin() {
    QModelIndexList selectedIndexes = this->tutorialsListWidget->selectionModel()->selectedIndexes();
    if (selectedIndexes.size() == 0){
        this->presenterLineEdit->clear();
        this->titleLineEdit->clear();
        this->minLineEdit->clear();
        this->secLineEdit->clear();
        this->likesLineEdit->clear();
        this->linkLineEdit->clear();
        return -1;
    }
    int selectedIndex = selectedIndexes.at(0).row();
    return selectedIndex;
}

void GUI::addTutorialAdmin() {
    std::string presenter = this->presenterLineEdit->text().toStdString();
    std::string title = this->titleLineEdit->text().toStdString();
    std::string minutes = this->minLineEdit->text().toStdString();
    std::string seconds = this->secLineEdit->text().toStdString();
    std::string likes = this->likesLineEdit->text().toStdString();
    std::string link = this->linkLineEdit->text().toStdString();

    try {
        this->serv->add_tutorial_service(title, presenter, minutes, seconds, likes, link);
    } catch (RepoException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", "Cannot add element! Tutorial already exists");
    }
    catch (ValidationException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("Invalid data!"));
    }

    this->populateListAdmin();

    int lastElement = this->serv->get_repo()->get_tutorials()->size() - 1;
    this->tutorialsListWidget->setCurrentRow(lastElement);


}

void GUI::removeTutorialAdmin() {
    std::string presenter = this->presenterLineEdit->text().toStdString();
    std::string title = this->titleLineEdit->text().toStdString();
    try {
        this->serv->remove_tutorial_service(presenter, title);
    } catch (RepoException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", "This tutorial doesn't exist!");
    }
    catch (ValidationException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("Invalid data!"));
    }
    this->populateListAdmin();
}

void GUI::updateTutorialAdmin() {
    int selectedIndex = this->getSelectedIndexAdmin();

    if (selectedIndex < 0)
        return;
    std::vector<Tutorial> *allTutorials = this->serv->get_repo()->get_tutorials();
    Tutorial t;
    int i = 0;
    for (auto& it : *allTutorials)
    {
        if (i == selectedIndex){
            t = it;
            break;
        }
        i++;
    }

    std::string presenter = this->presenterLineEdit->text().toStdString();
    std::string title = this->titleLineEdit->text().toStdString();
    std::string minutes = this->minLineEdit->text().toStdString();
    std::string seconds = this->secLineEdit->text().toStdString();
    std::string likes = this->likesLineEdit->text().toStdString();
    std::string link = this->linkLineEdit->text().toStdString();

    try {
        this->serv->update_tutorial_gui_service(t.get_presenter(), t.get_title(), std::to_string(t.get_duration().getMinutes()), std::to_string(t.get_duration().getSeconds()), std::to_string(t.get_no_of_likes()), t.get_link(), presenter, title, minutes, seconds, likes, link);
    } catch (RepoException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", "Cannot update element! Tutorial already exists!");
    }
    catch (ValidationException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("Invalid data!"));
    }

    this->populateListAdmin();

    int lastElement = this->serv->get_repo()->get_tutorials()->size() - 1;
    this->tutorialsListWidget->setCurrentRow(lastElement);
}

void GUI::initGUIUser() {
    this->watchlist = new QListWidget{};

    this->addWatchlistButton = new QPushButton{"Add to watchlist"};
    this->viewWatchlistButton = new QPushButton{"View watchlist"};
    this->deleteWatchlistButton = new QPushButton{"Delete from watchlist"};

    auto* mainLayout = new QVBoxLayout{ this };

    mainLayout->addWidget(this->watchlist);

    auto* buttonsLayout = new QGridLayout{};
    buttonsLayout->addWidget(this->viewWatchlistButton, 0, 0);
    buttonsLayout->addWidget(this->addWatchlistButton, 0, 1);
    buttonsLayout->addWidget(this->deleteWatchlistButton, 0, 2);



    mainLayout->addLayout(buttonsLayout);
}

void GUI::populateListUser() {
    this->watchlist->clear();
    std::vector<Tutorial> *allFromWatchlist = serv->get_wathlist()->get_tutorials();
    for (Tutorial& t: *allFromWatchlist){
        this->watchlist->addItem(QString::fromStdString(t.get_presenter() + ", " + t.get_title() + ", " + std::to_string(t.get_duration().getMinutes()) + ":" + std::to_string(t.get_duration().getSeconds()) + ", " + std::to_string(t.get_no_of_likes()) + ", " + t.get_link()));
    }
}

