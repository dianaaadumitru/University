/********************************************************************************
** Form generated from reading UI file 'gui3.ui'
**
** Created by: Qt User Interface Compiler version 5.15.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_GUI3_H
#define UI_GUI3_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTableView>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_addWatchlistButton
{
public:
    QTableView *tutorialView;
    QLabel *Tutorials;
    QTableView *watchlistView;
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
    QPushButton *addButton_;

    void setupUi(QWidget *addWatchlistButton)
    {
        if (addWatchlistButton->objectName().isEmpty())
            addWatchlistButton->setObjectName(QString::fromUtf8("addWatchlistButton"));
        addWatchlistButton->resize(1910, 981);
        tutorialView = new QTableView(addWatchlistButton);
        tutorialView->setObjectName(QString::fromUtf8("tutorialView"));
        tutorialView->setGeometry(QRect(10, 30, 831, 561));
        Tutorials = new QLabel(addWatchlistButton);
        Tutorials->setObjectName(QString::fromUtf8("Tutorials"));
        Tutorials->setGeometry(QRect(290, 0, 151, 31));
        watchlistView = new QTableView(addWatchlistButton);
        watchlistView->setObjectName(QString::fromUtf8("watchlistView"));
        watchlistView->setGeometry(QRect(900, 30, 951, 561));
        label = new QLabel(addWatchlistButton);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(1410, 0, 91, 21));
        presenterLineEdit = new QLineEdit(addWatchlistButton);
        presenterLineEdit->setObjectName(QString::fromUtf8("presenterLineEdit"));
        presenterLineEdit->setGeometry(QRect(100, 630, 401, 31));
        label_2 = new QLabel(addWatchlistButton);
        label_2->setObjectName(QString::fromUtf8("label_2"));
        label_2->setGeometry(QRect(20, 630, 71, 31));
        titleLineEdit_2 = new QLineEdit(addWatchlistButton);
        titleLineEdit_2->setObjectName(QString::fromUtf8("titleLineEdit_2"));
        titleLineEdit_2->setGeometry(QRect(100, 680, 401, 31));
        minLineEdit = new QLineEdit(addWatchlistButton);
        minLineEdit->setObjectName(QString::fromUtf8("minLineEdit"));
        minLineEdit->setGeometry(QRect(100, 730, 401, 31));
        secLineEdit = new QLineEdit(addWatchlistButton);
        secLineEdit->setObjectName(QString::fromUtf8("secLineEdit"));
        secLineEdit->setGeometry(QRect(100, 780, 401, 31));
        likesLineEdit = new QLineEdit(addWatchlistButton);
        likesLineEdit->setObjectName(QString::fromUtf8("likesLineEdit"));
        likesLineEdit->setGeometry(QRect(100, 830, 401, 31));
        linkLineEdit = new QLineEdit(addWatchlistButton);
        linkLineEdit->setObjectName(QString::fromUtf8("linkLineEdit"));
        linkLineEdit->setGeometry(QRect(100, 880, 401, 31));
        label_3 = new QLabel(addWatchlistButton);
        label_3->setObjectName(QString::fromUtf8("label_3"));
        label_3->setGeometry(QRect(20, 690, 51, 21));
        label_4 = new QLabel(addWatchlistButton);
        label_4->setObjectName(QString::fromUtf8("label_4"));
        label_4->setGeometry(QRect(20, 740, 71, 16));
        label_5 = new QLabel(addWatchlistButton);
        label_5->setObjectName(QString::fromUtf8("label_5"));
        label_5->setGeometry(QRect(20, 790, 71, 16));
        label_6 = new QLabel(addWatchlistButton);
        label_6->setObjectName(QString::fromUtf8("label_6"));
        label_6->setGeometry(QRect(20, 840, 61, 16));
        label_7 = new QLabel(addWatchlistButton);
        label_7->setObjectName(QString::fromUtf8("label_7"));
        label_7->setGeometry(QRect(20, 880, 61, 20));
        addButton = new QPushButton(addWatchlistButton);
        addButton->setObjectName(QString::fromUtf8("addButton"));
        addButton->setGeometry(QRect(20, 930, 141, 31));
        removeButton = new QPushButton(addWatchlistButton);
        removeButton->setObjectName(QString::fromUtf8("removeButton"));
        removeButton->setGeometry(QRect(190, 930, 141, 31));
        updateButton = new QPushButton(addWatchlistButton);
        updateButton->setObjectName(QString::fromUtf8("updateButton"));
        updateButton->setGeometry(QRect(360, 930, 141, 31));
        viewWatchlistButton = new QPushButton(addWatchlistButton);
        viewWatchlistButton->setObjectName(QString::fromUtf8("viewWatchlistButton"));
        viewWatchlistButton->setGeometry(QRect(930, 930, 141, 31));
        addButton_4 = new QPushButton(addWatchlistButton);
        addButton_4->setObjectName(QString::fromUtf8("addButton_4"));
        addButton_4->setGeometry(QRect(1100, 930, 141, 31));
        removeWatchlistButton = new QPushButton(addWatchlistButton);
        removeWatchlistButton->setObjectName(QString::fromUtf8("removeWatchlistButton"));
        removeWatchlistButton->setGeometry(QRect(1260, 930, 141, 31));
        addButton_ = new QPushButton(addWatchlistButton);
        addButton_->setObjectName(QString::fromUtf8("addButton_"));
        addButton_->setGeometry(QRect(1420, 930, 141, 31));

        retranslateUi(addWatchlistButton);

        QMetaObject::connectSlotsByName(addWatchlistButton);
    } // setupUi

    void retranslateUi(QWidget *addWatchlistButton)
    {
        addWatchlistButton->setWindowTitle(QCoreApplication::translate("addWatchlistButton", "gui3", nullptr));
        Tutorials->setText(QCoreApplication::translate("addWatchlistButton", "Tutorials", nullptr));
        label->setText(QCoreApplication::translate("addWatchlistButton", "Watchlist", nullptr));
        label_2->setText(QCoreApplication::translate("addWatchlistButton", "Presenter", nullptr));
        label_3->setText(QCoreApplication::translate("addWatchlistButton", "Title", nullptr));
        label_4->setText(QCoreApplication::translate("addWatchlistButton", "Minutes", nullptr));
        label_5->setText(QCoreApplication::translate("addWatchlistButton", "Seconds", nullptr));
        label_6->setText(QCoreApplication::translate("addWatchlistButton", "Likes", nullptr));
        label_7->setText(QCoreApplication::translate("addWatchlistButton", "Link", nullptr));
        addButton->setText(QCoreApplication::translate("addWatchlistButton", "Add", nullptr));
        removeButton->setText(QCoreApplication::translate("addWatchlistButton", "Remove", nullptr));
        updateButton->setText(QCoreApplication::translate("addWatchlistButton", "Update", nullptr));
        viewWatchlistButton->setText(QCoreApplication::translate("addWatchlistButton", "View watchlist", nullptr));
        addButton_4->setText(QCoreApplication::translate("addWatchlistButton", "Add to watchlist", nullptr));
        removeWatchlistButton->setText(QCoreApplication::translate("addWatchlistButton", "Remove from watchlist", nullptr));
        addButton_->setText(QCoreApplication::translate("addWatchlistButton", "Next", nullptr));
    } // retranslateUi

};

namespace Ui {
    class addWatchlistButton: public Ui_addWatchlistButton {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_GUI3_H
