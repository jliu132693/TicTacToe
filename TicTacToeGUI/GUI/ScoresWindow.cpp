#include "scoreswindow.h"
#include "ui_scoreswindow.h"

#include "iostream"

ScoresWindow::ScoresWindow(QWidget *parent) :
    QMainWindow(parent), ui(new Ui::ScoresWindow),
    parentWindow((QMainWindow*)parent)
{
    ui->setupUi(this);

    nam = new QNetworkAccessManager(this);
    QObject::connect(nam, SIGNAL(finished(QNetworkReply*)),
             this, SLOT(finishedSlot(QNetworkReply*)));

    CredentialSingleton & c = CredentialSingleton::getInstance();

    if (c.getLoginStatus()) {
        ui->loginStatusLabel->setText(c.getUsername().c_str());
    }

    getTopScores();
}

ScoresWindow::~ScoresWindow()
{
    delete ui;
}

void ScoresWindow::finishedSlot(QNetworkReply*reply)
{
    int statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();

    std::cout << "statusCode: " << statusCode << "\n";

    if (statusCode == 200) {  // valid login

        QByteArray bytes = reply->readAll();  // bytes
        QString string(bytes); // string

        std::string htmlOutput = string.toStdString();

        std::string currentUsername = "";
        std::string currentPoints = "";

        bool collectingUsername = true;
        // bar signals split between name and points; + signals next user
        for (int i = 0; i < htmlOutput.length(); i++) {
            if (collectingUsername) {
                if (htmlOutput[i] != '|') {
                    currentUsername += htmlOutput[i];
                } else {
                    collectingUsername = false;
                }
            } else {
                if (htmlOutput[i] != '+') {
                    currentPoints += htmlOutput[i];
                } else {
                    collectingUsername = true;

                    // username and points found, now add to list and then erase
                    std::string listEntry = "- Username: " + currentUsername + " AND Points: " + currentPoints;

                    currentUsername = ""; // reset
                    currentPoints = ""; // reset

                    ui->listWidget->addItem(listEntry.c_str());
                }
            }
        }

    } else {
        ui->listWidget->addItem("Unexpected server error. Try again later.");
    }


    delete reply;  // delete reply object (ownership in function)
}

void ScoresWindow::getTopScores()
{

    QUrl url("http://www.tictactoenetwork.appspot.com/topscores?specialFormat=true");
    QNetworkReply* reply = nam->get(QNetworkRequest(url));
}

void ScoresWindow::on_scoresBackButton_clicked()
{
    parentWindow->show();
    hide();
}
