#ifndef GAMEWINDOW_H
#define GAMEWINDOW_H

#include <QMainWindow>
#include <QLabel>
#include <QtNetwork/QNetworkInterface>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QUrl>
#include <QUrlQuery>
#include <QObject>
#include "CredentialSingleton.h"

namespace Ui {
class GameWindow;
}

class GameWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit GameWindow(QWidget *parent = 0);
    ~GameWindow();
    const int GRID_SIZE = 9;
    const int EMPTY_SQUARE = 0;
    int arr[9];
    int clickCount;
    int clickGridBox1Button;
    int clickGridBox2Button;
    int clickGridBox3Button;
    int clickGridBox4Button;
    int clickGridBox5Button;
    int clickGridBox6Button;
    int clickGridBox7Button;
    int clickGridBox8Button;
    int clickGridBox9Button;

private slots:
    void on_gameBackButton_clicked();
    void on_gridBox1Button_clicked();
    void on_gridBox2Button_clicked();
    void on_gridBox3Button_clicked();
    void on_gridBox4Button_clicked();
    void on_gridBox5Button_clicked();
    void on_gridBox6Button_clicked();
    void on_gridBox7Button_clicked();
    void on_gridBox8Button_clicked();
    void on_gridBox9Button_clicked();

private:
    Ui::GameWindow *ui;
    QMainWindow *parentWindow;
    QLabel *labelCheckResult1;

    void updateScores();
    bool checkIsFull();
    void checkResult();

    QNetworkAccessManager *nam;
};

#endif // GAMEWINDOW_H
