package main;

import service.LoginService;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * Created by Dragos on 3/29/2017.
 */
public class LoginServer
{
    private LoginService logmanager;
    private SSLServerSocketFactory socketFactory;
    private SSLServerSocket serverSocket;

    public LoginServer() throws Exception
    {
        socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
        logmanager = new LoginService();
    }

    public void runServer(final Main loginManager) {
        while (true) {
            try {
                System.err.println("Waiting for connection...");
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                System.out.println("Connected");
                String[] cipherSuites = socketFactory.getSupportedCipherSuites();
                socket.setEnabledCipherSuites(cipherSuites);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                String userName = input.readLine();
                String password = input.readLine();
                System.out.println(userName);
                System.out.println(password);
                String validare = logmanager.initManager(userName,password);
                if (validare.compareTo("valid") == 0)
                {
                    //loginManager.authenticated();
                    output.println("Valid");
                } else {
                    output.println("Invalid");
                }
                output.close();
                input.close();
                socket.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    }
