#pragma once
#include <QAbstractTableModel>
#include "IFileRepo.h"

class tutorialModel : public QAbstractTableModel{
private:
    IFileRepo *repo;

public:
    tutorialModel(IFileRepo * repo);
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const;
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;

    IFileRepo* getRepo() { return repo; };
    bool addTutorialToRepository(Tutorial t);
    bool removeTutorialFromRepository(int row);
    void likeTutorial(Tutorial &t);
    bool setData(const QModelIndex& index, const QVariant& value, int role) override;
    Qt::ItemFlags flags(const QModelIndex& index) const override;
};

