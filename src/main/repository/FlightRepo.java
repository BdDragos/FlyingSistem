package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Flight;
import java.sql.*;
import java.util.*;

public class FlightRepo
{
    private List<Flight> flights = new ArrayList<Flight>();
    private Statement statement;
    private Connection connection;

    public FlightRepo() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/flights";
        String user = "root";
        String password = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();
            statement.execute("USE flights");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String query = "select * from routes";
        ResultSet result = statement.executeQuery(query);
        while(result.next())
        {
            Integer id = result.getInt("Id");
            String dest = result.getString("Destination");
            String airp = result.getString("Airport");
            Integer frees = result.getInt("FreeSeats");
            Date dateh = result.getDate("Date");
            Flight flight = new Flight(id,dest,airp,frees,dateh);

            flights.add(flight);
        }
    }

    public List<Flight> getAll()
    {
        return flights;
    }


    public List<Flight> findByDestinationAndDate(String dest, Date data)
    {
        List<Flight> rez = new ArrayList<Flight>();
        for(Flight flight : flights)
            if(flight.getDestination().equals(dest) && flight.getDatehour().compareTo(data) == 0)
                rez.add(flight);
        return rez;
    }

    public List<Flight> findByDestination(String dest)
    {
        List<Flight> rez = new ArrayList<Flight>();
        for(Flight flight : flights)
            if(flight.getDestination().equals(dest))
                rez.add(flight);
        return rez;
    }

    public List<Flight> findByDate(Date data)
    {
        List<Flight> rez = new ArrayList<Flight>();
        for(Flight flight : flights)
            if(flight.getDatehour().compareTo(data) == 0)
                rez.add(flight);
        return rez;
    }


    public void updateFlight(Flight flight,String client,int noticket,String address)
    {
        for (Flight f: flights)
        {
            if(f.getFlightId() == flight.getFlightId())
            {
                try
                {
                    String query = "update routes set FreeSeats=FreeSeats-? where Id = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    int id = flight.getFlightId();
                    preparedStmt.setInt(1, noticket);
                    preparedStmt.setInt(2, id);
                    preparedStmt.executeUpdate();

                    int bilete =  flights.get(flight.getFlightId()-1).getFreeseats();
                    flights.get(flight.getFlightId()-1).setFreeseats(bilete-noticket);
                    String s = flight.toString();
                    if (flights.get(flight.getFlightId()-1).getFreeseats() == 0)
                    {
                        deleteFlight(flight.getFlightId());
                    }

                    String query2 = " insert into clients (Name,Address,NoTickets,Flight)"
                            + " values (?, ?, ?, ?)";

                    PreparedStatement preparedStmt2 = connection.prepareStatement(query2);
                    preparedStmt2.setString (1, client);
                    preparedStmt2.setString (2, address);
                    preparedStmt2.setInt   (3, noticket);
                    preparedStmt2.setString   (4, s);
                    preparedStmt2.execute();

                }
                catch (Exception e)
                {
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void deleteFlight(int id)
    {
        for (Flight f: flights)
        {
            if (f.getFlightId() == id)
            {
                try
                {
                    String query = "delete from routes where Id = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, id);
                    preparedStmt.execute();
                    flights.remove(id);
                }
                catch (Exception e)
                {
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public Flight findById(int id)
    {
        for (Flight f: flights)
            if (f.getFlightId() == id)
                return f;
        return null;
    }

}
