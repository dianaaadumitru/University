/********************************************************************************
** Form generated from reading UI file 'gui5.ui'
**
** Created by: Qt User Interface Compiler version 5.15.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_GUI5_H
#define UI_GUI5_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTableView>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_gui5
{
public:
    QTableView *tutorialView;
    QLabel *Tutorials;
    QLabel *label;
    QLineEdit *presenterLineEdit;
    QLabel *label_2;
    QLineEdit *titleLineEdit_2;
    QLineEdit *minLineEdit;
    QLineEdit *secLineEdit;
    QLineEdit *likesLineEdit;
    QLineEdit *linkLineEdit;
    QLabel *label_3;
    QLabel *label_4;
    QLabel *label_5;
    QLabel *label_6;
    QLabel *label_7;
    QPushButton *addButton;
    QPushButton *removeButton;
    QPushButton *updateButton;
    QPushButton *viewWatchlistButton;
    QPushButton *addButton_4;
    QPushButton *removeWatchlistButton;
    QPushButton *nextButton;
    QLineEdit *presenterFilter;
    QLabel *label_8;
    QPushButton *searchButton;
    QLabel *label_9;
    QLabel *presenterLabel;
    QLabel *label_10;
    QLabel *label_11;
    QLabel *label_12;
    QLabel *label_13;
    QLabel *titlelabel;
    QLabel *linkLabel;
    QLabel *noLikesLabel_2;
    QLabel *durationLabel;
    QTableView *watchlistView;

    void setupUi(QWidget *gui5)
    {
        if (gui5->objectName().isEmpty())
            gui5->setObjectName(QString::fromUtf8("gui5"));
        gui5->resize(1910, 981);
        tutorialView = new QTableView(gui5);
        tutorialView->setObjectName(QString::fromUtf8("tutorialView"));
        tutorialView->setGeometry(QRect(10, 80, 861, 511));
        Tutorials = new QLabel(gui5);
        Tutorials->setObjectName(QString::fromUtf8("Tutorials"));
        Tutorials->setGeometry(QRect(310, 30, 471, 31));
        QFont font;
        font.setPointSize(18);
        font.setBold(false);
        font.setWeight(50);
        Tutorials->setFont(font);
        label = new QLabel(gui5);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(1280, 30, 511, 31));
        label->setFont(font);
        presenterLineEdit = new QLineEdit(gui5);
        presenterLineEdit->setObjectName(QString::fromUtf8("presenterLineEdit"));
        presenterLineEdit->setGeometry(QRect(100, 630, 401, 31));
        label_2 = new QLabel(gui5);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(20, 630, 71, 31));
        titleLineEdit_2 = new QLineEdit(gui5);
        titleLineEdit_2->setObjectName(QString::fromUtf8("titleLineEdit_2"));
        titleLineEdit_2->setGeometry(QRect(100, 680, 401, 31));
        minLineEdit = new QLineEdit(gui5);
        minLineEdit->setObjectName(QString::fromUtf8("minLineEdit"));
        minLineEdit->setGeometry(QRect(100, 730, 401, 31));
        secLineEdit = new QLineEdit(gui5);
        secLineEdit->setObjectName(QString::fromUtf8("secLineEdit"));
        secLineEdit->setGeometry(QRect(100, 780, 401, 31));
        likesLineEdit = new QLineEdit(gui5);
        likesLineEdit->setObjectName(QString::fromUtf8("likesLineEdit"));
        likesLineEdit->setGeometry(QRect(100, 830, 401, 31));
        linkLineEdit = new QLineEdit(gui5);
        linkLineEdit->setObjectName(QString::fromUtf8("linkLineEdit"));
        linkLineEdit->setGeometry(QRect(100, 880, 401, 31));
        label_3 = new QLabel(gui5);
        label_3->setObjectName(QString::fromUtf8("label_3"));
        label_3->setGeometry(QRect(20, 690, 51, 21));
        label_4 = new QLabel(gui5);
        label_4->setObjectName(QString::fromUtf8("label_4"));
        label_4->setGeometry(QRect(20, 740, 71, 16));
        label_5 = new QLabel(gui5);
        label_5->setObjectName(QString::fromUtf8("label_5"));
        label_5->setGeometry(QRect(20, 790, 71, 16));
        label_6 = new QLabel(gui5);
        label_6->setObjectName(QString::fromUtf8("label_6"));
        label_6->setGeometry(QRect(20, 840, 61, 16));
        label_7 = new QLabel(gui5);
        label_7->setObjectName(QString::fromUtf8("label_7"));
        label_7->setGeometry(QRect(20, 880, 61, 20));
        addButton = new QPushButton(gui5);
        addButton->setObjectName(QString::fromUtf8("addButton"));
        addButton->setGeometry(QRect(20, 930, 141, 31));
        removeButton = new QPushButton(gui5);
        removeButton->setObjectName(QString::fromUtf8("removeButton"));
        removeButton->setGeometry(QRect(190, 930, 141, 31));
        updateButton = new QPushButton(gui5);
        updateButton->setObjectName(QString::fromUtf8("updateButton"));
        updateButton->setGeometry(QRect(360, 930, 141, 31));
        viewWatchlistButton = new QPushButton(gui5);
        viewWatchlistButton->setObjectName(QString::fromUtf8("viewWatchlistButton"));
        viewWatchlistButton->setGeometry(QRect(930, 930, 141, 31));
        addButton_4 = new QPushButton(gui5);
        addButton_4->setObjectName(QString::fromUtf8("addButton_4"));
        addButton_4->setGeometry(QRect(1100, 930, 141, 31));
        removeWatchlistButton = new QPushButton(gui5);
        removeWatchlistButton->setObjectName(QString::fromUtf8("removeWatchlistButton"));
        removeWatchlistButton->setGeometry(QRect(1260, 930, 171, 31));
        nextButton = new QPushButton(gui5);
        nextButton->setObjectName(QString::fromUtf8("nextButton"));
        nextButton->setGeometry(QRect(1450, 930, 141, 31));
        presenterFilter = new QLineEdit(gui5);
        presenterFilter->setObjectName(QString::fromUtf8("presenterFilter"));
        presenterFilter->setGeometry(QRect(1020, 630, 401, 31));
        label_8 = new QLabel(gui5);
        label_8->setObjectName(QString::fromUtf8("label_8"));
        label_8->setGeometry(QRect(910, 630, 71, 31));
        searchButton = new QPushButton(gui5);
        searchButton->setObjectName(QString::fromUtf8("searchButton"));
        searchButton->setGeometry(QRect(1470, 630, 141, 31));
        label_9 = new QLabel(gui5);
        label_9->setObjectName(QString::fromUtf8("label_9"));
        label_9->setGeometry(QRect(920, 710, 141, 20));
        QFont font1;
        font1.setPointSize(12);
        label_9->setFont(font1);
        presenterLabel = new QLabel(gui5);
        presenterLabel->setObjectName(QString::fromUtf8("presenterLabel"));
        presenterLabel->setGeometry(QRect(1070, 700, 511, 41));
        QPalette palette;
        QBrush brush(QColor(170, 0, 0, 255));
        brush.setStyle(Qt::SolidPattern);
        palette.setBrush(QPalette::Active, QPalette::WindowText, brush);
        palette.setBrush(QPalette::Inactive, QPalette::WindowText, brush);
        QBrush brush1(QColor(120, 120, 120, 255));
        brush1.setStyle(Qt::SolidPattern);
        palette.setBrush(QPalette::Disabled, QPalette::WindowText, brush1);
        presenterLabel->setPalette(palette);
        QFont font2;
        font2.setFamily(QString::fromUtf8("Trebuchet MS"));
        font2.setPointSize(12);
        font2.setBold(true);
        font2.setItalic(true);
        font2.setWeight(75);
        presenterLabel->setFont(font2);
        label_10 = new QLabel(gui5);
        label_10->setObjectName(QString::fromUtf8("label_10"));
        label_10->setGeometry(QRect(920, 750, 91, 20));
        label_10->setFont(font1);
        label_11 = new QLabel(gui5);
        label_11->setObjectName(QString::fromUtf8("label_11"));
        label_11->setGeometry(QRect(920, 870, 101, 20));
        label_11->setFont(font1);
        label_12 = new QLabel(gui5);
        label_12->setObjectName(QString::fromUtf8("label_12"));
        label_12->setGeometry(QRect(920, 790, 121, 20));
        label_12->setFont(font1);
        label_13 = new QLabel(gui5);
        label_13->setObjectName(QString::fromUtf8("label_13"));
        label_13->setGeometry(QRect(920, 830, 121, 20));
        label_13->setFont(font1);
        titlelabel = new QLabel(gui5);
        titlelabel->setObjectName(QString::fromUtf8("titlelabel"));
        titlelabel->setGeometry(QRect(1070, 739, 461, 41));
        QPalette palette1;
        palette1.setBrush(QPalette::Active, QPalette::WindowText, brush);
        palette1.setBrush(QPalette::Inactive, QPalette::WindowText, brush);
        palette1.setBrush(QPalette::Disabled, QPalette::WindowText, brush1);
        titlelabel->setPalette(palette1);
        titlelabel->setFont(font2);
        linkLabel = new QLabel(gui5);
        linkLabel->setObjectName(QString::fromUtf8("linkLabel"));
        linkLabel->setGeometry(QRect(1070, 860, 551, 41));
        QPalette palette2;
        palette2.setBrush(QPalette::Active, QPalette::WindowText, brush);
        palette2.setBrush(QPalette::Inactive, QPalette::WindowText, brush);
        palette2.setBrush(QPalette::Disabled, QPalette::WindowText, brush1);
        linkLabel->setPalette(palette2);
        linkLabel->setFont(font2);
        noLikesLabel_2 = new QLabel(gui5);
        noLikesLabel_2->setObjectName(QString::fromUtf8("noLikesLabel_2"));
        noLikesLabel_2->setGeometry(QRect(1070, 830, 261, 20));
        QPalette palette3;
        palette3.setBrush(QPalette::Active, QPalette::WindowText, brush);
        palette3.setBrush(QPalette::Inactive, QPalette::WindowText, brush);
        palette3.setBrush(QPalette::Disabled, QPalette::WindowText, brush1);
        noLikesLabel_2->setPalette(palette3);
        noLikesLabel_2->setFont(font2);
        durationLabel = new QLabel(gui5);
        durationLabel->setObjectName(QString::fromUtf8("durationLabel"));
        durationLabel->setGeometry(QRect(1070, 790, 301, 20));
        QPalette palette4;
        palette4.setBrush(QPalette::Active, QPalette::WindowText, brush);
        palette4.setBrush(QPalette::Inactive, QPalette::WindowText, brush);
        palette4.setBrush(QPalette::Disabled, QPalette::WindowText, brush1);
        durationLabel->setPalette(palette4);
        durationLabel->setFont(font2);
        watchlistView = new QTableView(gui5);
        watchlistView->setObjectName(QString::fromUtf8("watchlistView"));
        watchlistView->setGeometry(QRect(910, 80, 861, 511));

        retranslateUi(gui5);

        QMetaObject::connectSlotsByName(gui5);
    } // setupUi

    void retranslateUi(QWidget *gui5)
    {
        gui5->setWindowTitle(QCoreApplication::translate("gui5", "gui5", nullptr));
        Tutorials->setText(QCoreApplication::translate("gui5", "AdministratorMode", nullptr));
        label->setText(QCoreApplication::translate("gui5", "UserMode", nullptr));
        label_2->setText(QCoreApplication::translate("gui5", "Presenter", nullptr));
        label_3->setText(QCoreApplication::translate("gui5", "Title", nullptr));
        label_4->setText(QCoreApplication::translate("gui5", "Minutes", nullptr));
        label_5->setText(QCoreApplication::translate("gui5", "Seconds", nullptr));
        label_6->setText(QCoreApplication::translate("gui5", "Likes", nullptr));
        label_7->setText(QCoreApplication::translate("gui5", "Link", nullptr));
        addButton->setText(QCoreApplication::translate("gui5", "Add", nullptr));
        removeButton->setText(QCoreApplication::translate("gui5", "Remove", nullptr));
        updateButton->setText(QCoreApplication::translate("gui5", "Update", nullptr));
        viewWatchlistButton->setText(QCoreApplication::translate("gui5", "View watchlist", nullptr));
        addButton_4->setText(QCoreApplication::translate("gui5", "Add to watchlist", nullptr));
        removeWatchlistButton->setText(QCoreApplication::translate("gui5", "Remove from watchlist", nullptr));
        nextButton->setText(QCoreApplication::translate("gui5", "Next", nullptr));
        label_8->setText(QCoreApplication::translate("gui5", "Presenter", nullptr));
        searchButton->setText(QCoreApplication::translate("gui5", "Search", nullptr));
        label_9->setText(QCoreApplication::translate("gui5", "Presenter:", nullptr));
        presenterLabel->setText(QCoreApplication::translate("gui5", "No tutorial Selected", nullptr));
        label_10->setText(QCoreApplication::translate("gui5", "Title:", nullptr));
        label_11->setText(QCoreApplication::translate("gui5", "Link:", nullptr));
        label_12->setText(QCoreApplication::translate("gui5", "Duration:", nullptr));
        label_13->setText(QCoreApplication::translate("gui5", "No. Likes:", nullptr));
        titlelabel->setText(QCoreApplication::translate("gui5", "No tutorial Selected", nullptr));
        linkLabel->setText(QCoreApplication::translate("gui5", "No tutorial Selected", nullptr));
        noLikesLabel_2->setText(QCoreApplication::translate("gui5", "No tutorial Selected", nullptr));
        durationLabel->setText(QCoreApplication::translate("gui5", "No tutorial Selected", nullptr));
    } // retranslateUi

};

namespace Ui {
    class gui5: public Ui_gui5 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_GUI5_H
