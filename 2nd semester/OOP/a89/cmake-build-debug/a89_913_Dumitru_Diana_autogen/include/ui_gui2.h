/********************************************************************************
** Form generated from reading UI file 'gui2.ui'
**
** Created by: Qt User Interface Compiler version 5.15.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_GUI2_H
#define UI_GUI2_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QTableView>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_gui2
{
public:
    QTableView *tutorialView;

    void setupUi(QWidget *gui2)
    {
        if (gui2->objectName().isEmpty())
            gui2->setObjectName(QString::fromUtf8("gui2"));
        gui2->resize(459, 379);
        tutorialView = new QTableView(gui2);
        tutorialView->setObjectName(QString::fromUtf8("tutorialView"));
        tutorialView->setGeometry(QRect(0, 30, 256, 291));

        retranslateUi(gui2);

        QMetaObject::connectSlotsByName(gui2);
    } // setupUi

    void retranslateUi(QWidget *gui2)
    {
        gui2->setWindowTitle(QCoreApplication::translate("gui2", "gui2", nullptr));
    } // retranslateUi

};

namespace Ui {
    class gui2: public Ui_gui2 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_GUI2_H
