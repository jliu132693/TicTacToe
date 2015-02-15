
#include "LoginWindow.h"
#include "MainWindow.h"
#include "ui_loginWindow.h"

#include "iostream"

LoginWindow::LoginWindow(QWidget *parent) :
    QMainWindow(parent), parentWindow((QMainWindow*)parent),
    ui(new Ui::LoginWindow)
{
    ui->setupUi(this);

    nam = new QNetworkAccessManager(this);
    QObject::connect(nam, SIGNAL(finished(QNetworkReply*)),
             this, SLOT(finishedSlot(QNetworkReply*)));

}

LoginWindow::~LoginWindow()
{
    delete ui;
}

void LoginWindow::finishedSlot(QNetworkReply*reply)
{
    int statusCode = reply->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();

    if (statusCode == 302) {  // valid login

        CredentialSingleton &c = CredentialSingleton::getInstance();

        c.setUsername(ui->usernameText->text().toStdString());
        c.setPassword(ui->passwordText->text().toStdString());
        c.setLoginStatus(true);

        on_backToMainWindowButton_clicked();
    } else if (statusCode == 400) { // invalid login
        ui->errorText->setText("Invalid login information. Try again.");
    } else { // unexpected http code
        ui->errorText->setText("Unexpected error found. Try again later.");
    }

    ui->passwordText->setText("");
    ui->usernameText->setText("");

    delete reply;  // delete reply object (ownership in function)
}

void LoginWindow::on_loginButton_clicked()
{
    std::string url = "http://www.tictactoenetwork.appspot.com/login";

    QUrlQuery postData;

    postData.addQueryItem("username", ui->usernameText->text());
    postData.addQueryItem("password", ui->passwordText->text());

    QNetworkRequest request(QUrl(url.c_str()));
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/x-www-form-urlencoded");
    nam->post(request, postData.toString().toUtf8());
}

void LoginWindow::on_backToMainWindowButton_clicked()
{
    emit visibilityChanged();

    parentWindow->show();
    hide();
}
