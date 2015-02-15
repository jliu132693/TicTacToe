#include "CredentialSingleton.h"

CredentialSingleton::~CredentialSingleton()
{ }

std::string CredentialSingleton::getPassword()
{
    return password;
}

void CredentialSingleton::setPassword(std::string password)
{
    this->password = password;
}

std::string CredentialSingleton::getUsername()
{
    return username;
}

void CredentialSingleton::setUsername(std::string username)
{
    this->username = username;
}


bool CredentialSingleton::getLoginStatus()
{
    return isLoggedIn;
}

void CredentialSingleton::setLoginStatus(bool loginStatus)
{
    isLoggedIn = loginStatus;
}
