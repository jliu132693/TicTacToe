#ifndef LOGINWINDOW_H
#define LOGINWINDOW_H

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
class LoginWindow;
}

class LoginWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit LoginWindow(QWidget *parent = 0);
    ~LoginWindow();

private slots:
    void on_loginButton_clicked();
    void on_backToMainWindowButton_clicked();
    void finishedSlot(QNetworkReply*reply);

signals:
    void visibilityChanged();

private:
    Ui::LoginWindow *ui;
    QMainWindow *parentWindow;

    QNetworkAccessManager *nam;
};

#endif // LOGINWINDOW_H
