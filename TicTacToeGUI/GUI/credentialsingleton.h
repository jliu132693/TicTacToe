#ifndef CREDENTIALSINGLETON_H
#define CREDENTIALSINGLETON_H

#include "iostream"

class CredentialSingleton
{
public:
    static CredentialSingleton& getInstance()
    {
        static CredentialSingleton instance;
        return instance;
    }

    ~CredentialSingleton();

    std::string getPassword();
    void setPassword(std::string password);
    std::string getUsername();
    void setUsername(std::string username);

    bool getLoginStatus();
    void setLoginStatus(bool loginStatus);

private:

    explicit CredentialSingleton()
    {
        isLoggedIn = false;
    }

    CredentialSingleton(CredentialSingleton const&);

    std::string username;
    std::string password;
    bool isLoggedIn;

    static CredentialSingleton *instance;
};

#endif // CREDENTIALSINGLETON_H
