//
// Created by diana on 5/16/2021.
//

#ifndef A89_913_DUMITRU_DIANA_GUI5_H
#define A89_913_DUMITRU_DIANA_GUI5_H

#include <QWidget>
#include "../service/service.h"
#include "tutorialModel.h"
#include "NumericFilterProxyModel.h"

QT_BEGIN_NAMESPACE
namespace Ui { class gui5; }
QT_END_NAMESPACE

class gui5 : public QWidget {
Q_OBJECT

public:
    explicit gui5(Service *s, QWidget *parent = Q_NULLPTR);

    ~gui5() override;

private:
    Ui::gui5 *ui;
    Service *s;
    tutorialModel* watchlistModel;
    tutorialModel* model;
    NumericFilterProxyModel* viewsFilter;
    Iterator iterator;

    void connectSignalAndSlots();
    void addTutorial();
    void removeTutorial();
    void configureIterator();
    void updateTutorial();
    void removeFromWatchlist();
    void next();
    void viewWatchlist();
    void addToWatchlist();
    int getSelectedIndex();
    Tutorial getSelectedTutorial();
    void updateTutorialAdmin();
    void selectTutorial();
};

#endif //A89_913_DUMITRU_DIANA_GUI5_H
