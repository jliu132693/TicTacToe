#ifndef REGIWINDOW_H
#define REGIWINDOW_H

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
class RegiWindow;
}

class RegiWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit RegiWindow(QWidget *parent = 0);
    ~RegiWindow();

private slots:
    void on_submitButton_clicked();
    void on_backToLoginWindowButton_clicked();
    void finishedSlot(QNetworkReply*reply);

signals:
    void visibilityChanged();

private:
    Ui::RegiWindow *ui;
    QMainWindow *parentWindow;
    QNetworkAccessManager *nam;
};

#endif // REGIWINDOW_H
