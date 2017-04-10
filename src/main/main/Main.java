package main;

import repository.FlightRepo;
import service.FlightService;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main
{
    private SSLServerSocketFactory socketFactory;
    private SSLServerSocket serverSocket;
    private SSLSocket socket;
    private static FlightRepo repo;
    private static FlightService ctr;
    private ArrayList<ClientAdministrator> al = new ArrayList<ClientAdministrator>();
    private int i = 1;
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        repo = new FlightRepo();
        ctr = new FlightService(repo);
        Main mn = new Main();
        mn.startserver();
    }

    public void startserver()
    {
        try {
            socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
            System.err.println("Waiting for connection...");

            while (true) {
                socket = (SSLSocket) serverSocket.accept();
                String[] cipherSuites = socketFactory.getSupportedCipherSuites();
                socket.setEnabledCipherSuites(cipherSuites);
                System.out.println("Connection established: " + " " + i);
                ClientAdministrator cl = new ClientAdministrator(socket, this, ctr);
                Runnable r = cl;
                Thread t = new Thread(r);
                t.start();
                al.add(cl);
                i++;
            }
        } catch (IOException ex) {
            System.err.println("Eroare :" + ex.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                i--;
            } catch (IOException ex2) {
            }
        }
    }

    void broadcastMsg()
    {
        for (int j = 0; j < al.size(); ++j) {
            ClientAdministrator ch = al.get(j);
            ch.out.println("Updatare");
        }
    }
}


