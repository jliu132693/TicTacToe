package droid.se1.zhong.tictactoedroid;

import android.content.Context;


public class CredentialSingleton {

    private static CredentialSingleton sCS;

    private String username, password;
    private boolean isLoggedIn;

    private Context mAppContext;

    private CredentialSingleton(Context appContext)
    {
        mAppContext = appContext;
    }

    public static CredentialSingleton get(Context c) {
        if (sCS == null) {
            sCS = new CredentialSingleton(c.getApplicationContext());
        }

        return sCS;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setLoginStatus(boolean status)
    {
        isLoggedIn = status;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean getLoginStatus()
    {
        return isLoggedIn;
    }




}
