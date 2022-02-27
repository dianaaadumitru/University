#include <QMessageBox>
#include "gui5.h"
#include "ui_gui5.h"
#include "PlayButtonDelegate.h"
#include <sstream>
#include <QPushButton>

gui5::~gui5() {
    delete ui;
}

gui5::gui5(Service *s, QWidget *parent) :
        QWidget(parent), ui(new Ui::gui5), s(s) {
    ui->setupUi(this);

    model = new tutorialModel{ s->get_repo() };
    watchlistModel = new tutorialModel{ s->get_wathlist() };

    viewsFilter = new NumericFilterProxyModel{};
    viewsFilter->setSourceModel(model);
    ui->tutorialView->setModel(viewsFilter);
    ui->tutorialView->resizeColumnsToContents();
    ui->tutorialView->resizeRowsToContents();

    ui->watchlistView->setModel(watchlistModel);
    ui->watchlistView->resizeColumnsToContents();
    ui->watchlistView->resizeColumnToContents(5);
    ui->watchlistView->resizeRowsToContents();

    ui->tutorialView->setEditTriggers(QAbstractItemView::DoubleClicked);
    ui->tutorialView->setItemDelegate(new PlayButtonDelegate{});
    ui->watchlistView->setItemDelegate(new PlayButtonDelegate{});
    ui->tutorialView->setColumnWidth(6, 40);
    ui->watchlistView->setColumnWidth(6, 40);

    connectSignalAndSlots();
    selectTutorial();
}

void gui5::connectSignalAndSlots() {
    QObject::connect(ui->addButton, &QPushButton::clicked, this, &gui5::addTutorial);
    QObject::connect(ui->removeButton, &QPushButton::clicked, this, &gui5::removeTutorial);
    QObject::connect(ui->removeWatchlistButton, &QPushButton::clicked, this, &gui5::removeFromWatchlist);
    QObject::connect(ui->searchButton, &QPushButton::clicked, this, &gui5::configureIterator);
    QObject::connect(ui->nextButton, &QPushButton::clicked, this, &gui5::next);
    QObject::connect(ui->viewWatchlistButton, &QPushButton::clicked, this, &gui5::viewWatchlist);
    QObject::connect(ui->addButton_4, &QPushButton::clicked, this, &gui5::addToWatchlist);
    QObject::connect(ui->updateButton, &QPushButton::clicked, this, &gui5::updateTutorialAdmin);
    QObject::connect(ui->tutorialView, &QTableView::clicked, this, &gui5::selectTutorial);
}

void gui5::addTutorial() {
    std::string presenter = ui->presenterLineEdit->text().toStdString();
    std::string title = ui->titleLineEdit_2->text().toStdString();
    std::string min = ui->minLineEdit->text().toStdString();
    std::string sec = ui->secLineEdit->text().toStdString();
    std::string likes = ui->likesLineEdit->text().toStdString();
    std::string link = ui->linkLineEdit->text().toStdString();

    std::string tutorialString = title + ',' + presenter + ',' +  min + ',' + sec + ',' + likes + "," + link;

    std::stringstream stream{ tutorialString };
    try
    {
        Tutorial t;
        stream >> t;
        model->addTutorialToRepository(t);
    } catch (RepoException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", "Cannot add element! Tutorial already exists");
    }
    catch (ValidationException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("Invalid data!"));
    }

//    int lastElement = this->s->get_repo()->get_tutorials()->size() - 1;
//    ui->tutorialView->setCurrentRow(lastElement);

}

void gui5::removeTutorial() {
    QItemSelectionModel *select = ui->tutorialView->selectionModel();
    QModelIndexList s = select->selectedRows();
    if (s.isEmpty()) {
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("You didn't select any row!"));
        return;
    }
    auto index = s.at(0);
    if (index.isValid()){
        QString cellText = index.data().toString();
        int row = index.row();
        auto v = model->removeTutorialFromRepository(row);
    }
}

void gui5::configureIterator() {
    iterator.iterator_empty();
    std::string presenter = ui->presenterFilter->text().toStdString();
    std::vector<Tutorial>* s = model->getRepo()->get_tutorials();
    if (presenter.empty()) {
        for (auto& it : *s) {
            iterator.add(it);
        }
    }
    else {
        for (auto& it : *s) {
            if (it.get_presenter() == presenter)
                iterator.add(it);
        }
    }
    iterator.open();
    updateTutorial();
}

void gui5::updateTutorial() {
    Tutorial t = iterator.get_current_tutorial();
    ui->presenterLabel->setText(QString(t.get_presenter().c_str()));
    ui->titlelabel->setText(QString(t.get_title().c_str()));
    ui->linkLabel->setText(QString(t.get_link().c_str()));
    ui->noLikesLabel_2->setText(QString(std::to_string(t.get_no_of_likes()).c_str()));
    std::string duration = std::to_string(t.get_duration().getMinutes()) + ":" + std::to_string(t.get_duration().getSeconds());
    ui->durationLabel->setText(QString(duration.c_str()));
}

void gui5::removeFromWatchlist() {
    QItemSelectionModel *select = ui->watchlistView->selectionModel();
    QModelIndexList s = select->selectedRows();
    if (s.isEmpty()) {
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("You didn't select any tutorial!"));
        return;
    }
    auto index = s.at(0);
    if (index.isValid()) {
        QString cellText = index.data().toString();
        int row = index.row();


        QMessageBox msgBox{};
        msgBox.setWindowTitle("like");
        msgBox.setText("Do you want to like this video?");
        QPushButton *yes = msgBox.addButton("yes", QMessageBox::AcceptRole);
        QPushButton *no = msgBox.addButton("no", QMessageBox::ActionRole);
        QObject::connect(yes, &QPushButton::clicked, [this]() {
            Tutorial t = getSelectedTutorial();
            int l = t.get_no_of_likes() + 1;
            this->s->update_tutorial_gui_service(t.get_presenter(), t.get_title(),
                                                 std::to_string(t.get_duration().getMinutes()),
                                                 std::to_string(t.get_duration().getSeconds()),
                                                 std::to_string(t.get_no_of_likes()), t.get_link(), t.get_presenter(),
                                                 t.get_title(), std::to_string(t.get_duration().getMinutes()),
                                                 std::to_string(t.get_duration().getSeconds()), std::to_string(l),
                                                 t.get_link());
        });
        msgBox.exec();
        auto v = watchlistModel->removeTutorialFromRepository(row);
    }
}

void gui5::next() {
    iterator.next();
    updateTutorial();
}

void gui5::viewWatchlist() {
    this->s->get_wathlist()->repoToFile();
    this->s->get_wathlist()->display();
}

void gui5::addToWatchlist() {
    Tutorial t = iterator.get_current_tutorial();
    try {
        watchlistModel->addTutorialToRepository(t);
    } catch (RepoException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", "Cannot add element! Tutorial already exists");
    }
    catch (ValidationException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("Invalid data!"));
    }
}

int gui5::getSelectedIndex() {
    return ui->tutorialView->selectionModel()->currentIndex().row();
}

Tutorial gui5::getSelectedTutorial() {
    int selectedIndex = this->getSelectedIndex();
    if (selectedIndex < 0)
        return Tutorial();
    std::vector<Tutorial> *allTutorials = this->s->get_repo()->get_tutorials();
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
    return t;
}

void gui5::updateTutorialAdmin() {
    Tutorial t = getSelectedTutorial();
    std::string presenter = ui->presenterLineEdit->text().toStdString();
    std::string title = ui->titleLineEdit_2->text().toStdString();
    std::string min = ui->minLineEdit->text().toStdString();
    std::string sec = ui->secLineEdit->text().toStdString();
    std::string likes = ui->likesLineEdit->text().toStdString();
    std::string link = ui->linkLineEdit->text().toStdString();

    try {
        this->s->update_tutorial_gui_service(t.get_presenter(), t.get_title(), std::to_string(t.get_duration().getMinutes()), std::to_string(t.get_duration().getSeconds()), std::to_string(t.get_no_of_likes()), t.get_link(), presenter, title, min, sec, likes, link);
    } catch (RepoException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", "Cannot update element! Tutorial already exists!");
    }
    catch (ValidationException& e){
        QMessageBox messageBox;
        messageBox.critical(0, "Error", QString::fromStdString("Invalid data!"));
    }
}

void gui5::selectTutorial() {
    int selectedIndex = this->getSelectedIndex();
    if (selectedIndex < 0)
        return;
    std::vector<Tutorial> *allTutorials = this->s->get_repo()->get_tutorials();
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
    ui->presenterLineEdit->setText(QString::fromStdString(t.get_presenter()));
    ui->titleLineEdit_2->setText(QString::fromStdString(t.get_title()));
    ui->minLineEdit->setText(QString::fromStdString(std::to_string(t.get_duration().getMinutes())));
    ui->secLineEdit->setText(QString::fromStdString(std::to_string(t.get_duration().getSeconds())));
    ui->likesLineEdit->setText(QString::fromStdString(std::to_string(t.get_no_of_likes())));
    ui->linkLineEdit->setText(QString::fromStdString(t.get_link()));

//    int lastElement = this->s->get_repo()->get_tutorials()->size() - 1;
//    QModelIndex index = ui->tutorialView->rowAt(lastElement);
//    ui->tutorialView->setCurrentIndex(index);
}
