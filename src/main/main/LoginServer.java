package main;

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
    private static final String CORRECT_USER_NAME = "Java";

    private static final String CORRECT_PASSWORD = "Java";

    private SSLServerSocketFactory socketFactory;
    private SSLServerSocket serverSocket;

    public LoginServer() throws Exception
    {
        this.socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
    }

    public void runServer(final Main loginManager)
    {
        try
            {
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
                if (userName.equals(CORRECT_USER_NAME) && password.equals(CORRECT_PASSWORD))
                {
                    loginManager.authenticated();
                }
                else
                {
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
