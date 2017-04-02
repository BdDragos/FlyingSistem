package main;

import java.io.*;
import java.sql.SQLException;


import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Main
{
    private SSLServerSocketFactory socketFactory;
    private SSLServerSocket serverSocket;
    private SSLSocket socket;

    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        Main mn = new Main();
        mn.startserver();
    }
    public void startserver()
    {
        try {
            int i = 1;
            socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
            System.err.println("Waiting for connection...");

            while (true)
            {
                socket = (SSLSocket) serverSocket.accept();
                String[] cipherSuites = socketFactory.getSupportedCipherSuites();
                socket.setEnabledCipherSuites(cipherSuites);

                System.out.println("Connection established: " + " " + i);
                Runnable r = new ClientAdministrator(socket);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
        catch(IOException ex)
        {
            System.err.println("Eroare :"+ex.getMessage());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            try{socket.close();}
            catch(IOException ex2){}
        }
    }

}


