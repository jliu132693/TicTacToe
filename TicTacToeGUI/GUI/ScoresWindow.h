#ifndef SCORESWINDOW_H
#define SCORESWINDOW_H

#include <QMainWindow>
#include <QtNetwork/QNetworkInterface>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QUrl>
#include <QUrlQuery>
#include <QObject>
#include "CredentialSingleton.h"

namespace Ui {
class ScoresWindow;
}

class ScoresWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit ScoresWindow(QWidget *parent = 0);
    ~ScoresWindow();

private slots:
    void on_scoresBackButton_clicked();
    void finishedSlot(QNetworkReply*reply);



private:
    Ui::ScoresWindow *ui;
    QMainWindow *parentWindow;
    QNetworkAccessManager *nam;

    void getTopScores();

};

#endif // SCORESWINDOW_H
