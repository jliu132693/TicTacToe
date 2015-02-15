#include "RegiWindow.h"
#include "ui_RegiWindow.h"

#include "iostream"

RegiWindow::RegiWindow(QWidget *parent) :
    QMainWindow(parent), parentWindow((QMainWindow*)parent),
    ui(new Ui::RegiWindow)
{
    ui->setupUi(this);

    nam = new QNetworkAccessManager(this);
    QObject::connect(nam, SIGNAL(finished(QNetworkReply*)),
             this, SLOT(finishedSlot(QNetworkReply*)));
}

RegiWindow::~RegiWindow()
{
    delete ui;
}

void RegiWindow::finishedSlot(QNetworkReply*reply)
{
    int statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();

    if (statusCode == 302) {  // valid login
        CredentialSingleton &c = CredentialSingleton::getInstance();

        c.setUsername(ui->usernameText->text().toStdString());
        c.setPassword(ui->passwordText->text().toStdString());
        c.setLoginStatus(true);

        on_backToLoginWindowButton_clicked();

    } else if (statusCode == 401) { // username already exists

        ui->errorText->setText("Username already exists. Pick another.");
    } else if (statusCode == 400) { // invalid input

        ui->errorText->setText("Invalid input. Try again.");
    } else { // unexpected http code
        ui->errorText->setText("Unexpected error received. Try again later.");
    }

    // reset information
    ui->passwordText->setText("");
    ui->verifyText->setText("");
    ui->usernameText->setText("");

    delete reply;  // delete reply object (ownership in function)
}

void RegiWindow::on_submitButton_clicked()
{
    std::string url = "http://www.tictactoenetwork.appspot.com/register?";

    QUrlQuery postData;

    postData.addQueryItem("username", ui->usernameText->text());
    postData.addQueryItem("password", ui->passwordText->text());
    postData.addQueryItem("verify", ui->verifyText->text());

    QNetworkRequest request(QUrl(url.c_str()));
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/x-www-form-urlencoded");
    nam->post(request, postData.toString().toUtf8());
}

void RegiWindow::on_backToLoginWindowButton_clicked()
{
    emit visibilityChanged();

    parentWindow->show();
    hide();
}
