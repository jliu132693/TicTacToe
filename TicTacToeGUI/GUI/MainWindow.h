#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "LoginWindow.h"
#include "GameWindow.h"
#include "ScoresWindow.h"
#include "RegiWindow.h"
#include "CredentialSingleton.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_loginButton_clicked();
    void on_logoutButton_clicked();

    void on_playButton_clicked();

    void on_scoresButton_clicked();

    void on_regiButton_clicked();
    void mainVisibilityChange();

private:
    Ui::MainWindow *ui;
    LoginWindow *loginWindow;
    GameWindow *gameWindow;
    ScoresWindow *scoresWindow;
    RegiWindow *regiWindow;


};

#endif // MAINWINDOW_H
