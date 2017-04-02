package main;

import model.Flight;
import repository.FlightRepo;
import service.FlightService;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragos on 4/2/2017.
 */
class ClientAdministrator implements Runnable
{
    private List<Flight> lista = new ArrayList<Flight>();
    private FlightRepo repo = new FlightRepo();
    private FlightService ctr = new FlightService(repo);
    private SSLSocket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int NumarClienti = 0;

    public ClientAdministrator(SSLSocket socket) throws IOException, SQLException
    {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public void run()
    {
        try {
            String receive;
            while (true)
            {
                receive = in.readLine();
                if (receive.equals("END"))
                    break;
                if (receive.equals("Login"))
                {
                    try
                    {
                        String userName = in.readLine();
                        String password = in.readLine();
                        LoginServer server = new LoginServer();
                        int nr = server.runServer(userName,password);
                        if (nr == 1)
                        {
                            out.println("Valid");
                        }
                        else
                        {
                            out.println("Invalid");
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                if (receive.equals("Show"))
                {
                    String bigString = "";
                    lista = ctr.getAllFlights();

                    for (Flight i:lista)
                        bigString = bigString + "/" + i.getFlightId() + "," + i.getDestination() + "," + i.getAirport() + "," + i.getFreeseats() + "," + i.getDatehour();
                    out.println(bigString);
                }
            }
            System.out.println("closing...");
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                socket.close();
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}
