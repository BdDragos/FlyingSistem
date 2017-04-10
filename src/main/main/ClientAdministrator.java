package main;

import model.Flight;
import service.FlightService;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Created by Dragos on 4/2/2017
 */
class ClientAdministrator implements Runnable
{
    private List<Flight> lista = new ArrayList<Flight>();
    private FlightService ctr;
    private SSLSocket socket;
    private BufferedReader in;
    public PrintWriter out;
    private int NumarClienti = 0;
    private Main mn;

    public ClientAdministrator(SSLSocket socket,final Main mn,FlightService ctr) throws IOException, SQLException
    {
        this.ctr = ctr;
        this.mn = mn;
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
                if (receive.equals("SearchAll"))
                {
                    try
                    {
                        String depart = in.readLine();
                        String destin = in.readLine();

                        List<Flight> rez;

                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf1.parse(depart);
                        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                        rez = ctr.findByDestinationAndDate(destin,sqlStartDate);
                        String bigString = "";
                        for (Flight i:rez)
                            bigString = bigString + "/" + i.getFlightId() + "," + i.getDestination() + "," + i.getAirport() + "," + i.getFreeseats() + "," + i.getDatehour();
                        out.println(bigString);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (receive.equals("SearchDep"))
                {
                    try {
                        String depart = in.readLine();
                        List<Flight> rez;
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = sdf1.parse(depart);
                        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                        rez = ctr.findByDate(sqlStartDate);
                        String bigString = "";
                        for (Flight i : rez)
                            bigString = bigString + "/" + i.getFlightId() + "," + i.getDestination() + "," + i.getAirport() + "," + i.getFreeseats() + "," + i.getDatehour();
                        out.println(bigString);
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (receive.equals("SearchDest"))
                {
                    String destin = in.readLine();
                    List<Flight> rez;

                    rez = ctr.findByDestination(destin);
                    String bigString = "";
                    for (Flight i:rez)
                        bigString = bigString + "/" + i.getFlightId() + "," + i.getDestination() + "," + i.getAirport() + "," + i.getFreeseats() + "," + i.getDatehour();
                    out.println(bigString);
                }
                if (receive.equals("Buy"))
                {
                    String id = in.readLine();
                    String clientname = in.readLine();
                    String nrtickets = in.readLine();
                    String address = in.readLine();

                    int ok = ctr.buyFlight(Integer.parseInt(id),clientname,Integer.parseInt(nrtickets),address);
                    if (ok == 1)
                    {
                        out.println("Primit");
                    }
                    else
                    {
                        out.println("Invalid");
                    }
                }
            }
            System.out.println("closing...");
        } catch (IOException e)
        {
            System.out.println("IO Exception");
        }  finally {
            try {
                in.close();
                out.close();
                socket.close();
                System.out.println("The connection with a client was closed");
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}
