package model;

/**
 * Created by Dragos on 3/23/2017.
 */

public class User {

    private String user;
    private String parola;

    public User(String usr,String psd)
    {
        this.user=usr;
        this.parola=psd;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String val)
    {
        user=val;
    }

    public String getParola()
    {
        return parola;
    }

    public void setParola(String val)
    {
        parola=val;
    }

    public String toString()
    {
        return user+" "+parola;
    }



}
