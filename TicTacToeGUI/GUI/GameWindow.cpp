#include "gamewindow.h"
#include "ui_gamewindow.h"
#include <QLabel>

GameWindow::GameWindow(QWidget *parent) :
    QMainWindow(parent), parentWindow((QMainWindow*)parent),
    ui(new Ui::GameWindow)
{
    ui->setupUi(this);

    CredentialSingleton & c = CredentialSingleton::getInstance();

    if (c.getLoginStatus()) {
        ui->loginStatusLabel->setText(c.getUsername().c_str());
    }

    nam = new QNetworkAccessManager(this);

}

GameWindow::~GameWindow()
{
    delete ui;
}

void GameWindow::on_gameBackButton_clicked()
{
    parentWindow->show();
    hide();
}

void GameWindow::on_gridBox1Button_clicked()
{
    if (clickCount%2==0&&clickGridBox1Button==0)
    {
    ui->gridBox1Button->setText("X");
    clickCount++;
            clickGridBox1Button++;
            // change
            arr[1]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox1Button==0)
    {
    ui->gridBox1Button->setText("O");
    clickCount++;
            clickGridBox1Button++;
            arr[1]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox2Button_clicked()
{
    if (clickCount%2==0&&clickGridBox2Button==0)
    {
    ui->gridBox2Button->setText("X");
    clickCount++;
            clickGridBox2Button++;
            // change
            arr[2]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox2Button==0)
    {
    ui->gridBox2Button->setText("O");
    clickCount++;
            clickGridBox2Button++;
            arr[2]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox3Button_clicked()
{
    if (clickCount%2==0&&clickGridBox3Button==0)
    {
    ui->gridBox3Button->setText("X");
    clickCount++;
            clickGridBox3Button++;
            // change
            arr[3]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox3Button==0)
    {
    ui->gridBox3Button->setText("O");
    clickCount++;
            clickGridBox3Button++;
            arr[3]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox4Button_clicked()
{
    if (clickCount%2==0&&clickGridBox4Button==0)
    {
    ui->gridBox4Button->setText("X");
    clickCount++;
            clickGridBox4Button++;
            // change
            arr[4]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox4Button==0)
    {
    ui->gridBox4Button->setText("O");
    clickCount++;
            clickGridBox4Button++;
            arr[4]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox5Button_clicked()
{
    if (clickCount%2==0&&clickGridBox5Button==0)
    {
    ui->gridBox5Button->setText("X");
    clickCount++;
            clickGridBox5Button++;
            // change
            arr[5]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox5Button==0)
    {
    ui->gridBox5Button->setText("O");
    clickCount++;
            clickGridBox5Button++;
            arr[5]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox6Button_clicked()
{
    if (clickCount%2==0&&clickGridBox6Button==0)
    {
    ui->gridBox6Button->setText("X");
    clickCount++;
            clickGridBox6Button++;
            // change
            arr[6]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox6Button==0)
    {
    ui->gridBox6Button->setText("O");
    clickCount++;
            clickGridBox6Button++;
            arr[6]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox7Button_clicked()
{
    if (clickCount%2==0&&clickGridBox7Button==0)
    {
    ui->gridBox7Button->setText("X");
    clickCount++;
            clickGridBox7Button++;
            // change
            arr[7]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox7Button==0)
    {
    ui->gridBox7Button->setText("O");
    clickCount++;
            clickGridBox7Button++;
            arr[7]=1;
            if (clickCount>4)
                checkResult();
    }
}

void GameWindow::on_gridBox8Button_clicked()
{
    if (clickCount%2==0&&clickGridBox8Button==0)
    {
    ui->gridBox8Button->setText("X");
    clickCount++;
            clickGridBox8Button++;
            // change
            arr[8]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox8Button==0)
    {
    ui->gridBox8Button->setText("O");
    clickCount++;
            clickGridBox8Button++;
            arr[8]=1;
            if (clickCount>4)
                checkResult();
    }
}


void GameWindow::on_gridBox9Button_clicked()
{
    if (clickCount%2==0&&clickGridBox9Button==0)
    {
    ui->gridBox9Button->setText("X");
    clickCount++;
            clickGridBox9Button++;
            // change
            arr[9]=2;
            if (clickCount>4)
                checkResult();
    }

    if (clickCount%2==1&&clickGridBox9Button==0)
    {
    ui->gridBox9Button->setText("O");
    clickCount++;
            clickGridBox9Button++;
            arr[9]=1;
            if (clickCount>4)
                checkResult();
    }
}

bool GameWindow::checkIsFull()
{
    return arr[1]!=0&&arr[2]!=0&&arr[3]!=0&&arr[4]!=0&&arr[5]!=0&&arr[6]!=0&&arr[7]!=0&&arr[8]!=0&&arr[9]!=0;
}

void GameWindow::updateScores()
{
    CredentialSingleton & c = CredentialSingleton::getInstance();

    // if user is logged in, update scores
    if (c.getLoginStatus()) {

        std::string playURL = "http://www.tictactoenetwork.appspot.com/play?";
        playURL += "username=" + c.getUsername() + "&";
        playURL += "password=" + c.getPassword();

        QUrl url(playURL.c_str());
        QNetworkReply* reply = nam->get(QNetworkRequest(url));
    }
}

void GameWindow::checkResult()
{
    for(int i = 1; i <4; i++)
    {
        // x win
        if ((arr[5]==2)&&((arr[5-i] == arr[5] == arr[5+i])||(arr[1]==arr[2]==arr[3])||(arr[7]==arr[8]==arr[9])||(arr[1]==arr[4]==arr[7])||(arr[3]==arr[6]==arr[9])))
        {
            labelCheckResult1 = new QLabel("<h2><font color='blue'>X WON<\font><h2>");
            labelCheckResult1->show();

            updateScores();  // user is X - update score

            break;
        }
        //else
            //continue;
        // O win
        else if ((arr[5]==1)&&((arr[5-i] == arr[5] == arr[5+i])||(arr[1]==arr[2]==arr[3])||(arr[7]==arr[8]==arr[9])||(arr[1]==arr[4]==arr[7])||(arr[3]==arr[6]==arr[9])))
                {
                    labelCheckResult1 = new QLabel("<h2><font color='blue'>O WON<\font><h2>");
                    labelCheckResult1->show();
                    break;
                }
        else
        // Nobody win
                {
                    labelCheckResult1 = new QLabel("<h2><font color='blue'>NOBODY WON<\font><h2>");
                    labelCheckResult1->show();


                    updateScores(); // user managed to tie with AI; update score
                }
    }
}
