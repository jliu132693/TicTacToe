#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    ui->logoutButton->hide();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::mainVisibilityChange()
{
    CredentialSingleton &c = CredentialSingleton::getInstance();

    if (c.getLoginStatus()) {
        ui->loginButton->hide();
        ui->regiButton->hide();

        ui->logoutButton->show();

        ui->loginStatusLabel->setText(c.getUsername().c_str());
    }
}

void MainWindow::on_logoutButton_clicked()
{
    CredentialSingleton &c = CredentialSingleton::getInstance();

    c.setLoginStatus(false);
    c.setPassword("");
    c.setUsername("");

    ui->loginButton->show();
    ui->regiButton->show();

    ui->logoutButton->hide();

    ui->loginStatusLabel->setText("You aren't logged in");

}

void MainWindow::on_loginButton_clicked()
{
    loginWindow = new LoginWindow(this);
    loginWindow->show();
    hide();

    // notifies main of login (main should alter ui)
    QObject::connect(loginWindow,
                         SIGNAL(visibilityChanged()),
                         this,
                         SLOT(mainVisibilityChange()));
}

void MainWindow::on_playButton_clicked()
{
    gameWindow = new GameWindow(this);
    gameWindow->show();
    hide();

}

void MainWindow::on_scoresButton_clicked()
{
    scoresWindow = new ScoresWindow(this);
    scoresWindow->show();
    hide();
}

void MainWindow::on_regiButton_clicked()
{

    regiWindow= new RegiWindow(this);
    regiWindow->show();
    hide();

    // notifies main of login (main should alter ui)
    QObject::connect(regiWindow,
                         SIGNAL(visibilityChanged()),
                         this,
                         SLOT(mainVisibilityChange()));

}
