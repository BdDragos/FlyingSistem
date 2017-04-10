package main;

import service.LoginService;

/**
 * Created by Dragos on 3/29/2017.
 */
public class LoginServer
{
    private LoginService logmanager;

    public LoginServer() throws Exception
    {
        logmanager = new LoginService();
    }

    public int runServer(String userName,String password)
    {
        while (true)
        {
                String validare = logmanager.initManager(userName,password);
                if (validare.compareTo("valid") == 0)
                {
                    return 1;
                } else {
                    return 0;
                }
        }
    }
    }
