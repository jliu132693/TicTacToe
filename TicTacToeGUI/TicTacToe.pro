#-------------------------------------------------
#
# Project created by QtCreator 2014-08-20T16:50:28
#
#-------------------------------------------------

QT       += core gui
QT       += network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = TicTacToeGUI
TEMPLATE = app
LIBS+= -dead_strip

RC_FILE = icon.rc
ICON = icon.icns

SOURCES +=\
    GUI/GameWindow.cpp \
    GUI/main.cpp \
    GUI/MainWindow.cpp \
    GUI/ScoresWindow.cpp \
    GUI/LoginWindow.cpp \
    GUI/RegiWindow.cpp \
    GUI/CredentialSingleton.cpp

HEADERS  += \
    GUI/GameWindow.h \
    GUI/MainWindow.h \
    GUI/ScoresWindow.h \
    GUI/LoginWindow.h \
    GUI/RegiWindow.h \
    GUI/CredentialSingleton.h

FORMS    += \
    GUI/gamewindow.ui \
    GUI/mainwindow.ui \
    GUI/scoreswindow.ui \
    GUI/loginwindow.ui \
    GUI/regiwindow.ui
