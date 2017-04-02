package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public Flight findByDestinationAndDate(String dest, Date data)
    {
        for(Flight flight : flights)
            if(flight.getDestination().equals(dest) && flight.getDatehour().equals(data))
                return flight;

        return null;
    }


    public void updateFlight(Flight flight)
    {
        for (Flight f: flights)
        {
            if(f.getFlightId() == flight.getFlightId())
            {
                try
                {
                    String query = "update routes set FreeSeats=FreeSeats-1 where Id = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    int id = flight.getFlightId();
                    id=id+1;
                    preparedStmt.setInt(1, id);
                    preparedStmt.executeUpdate();
                    flights.get(f.getFlightId()).setFreeseats(f.getFreeseats()-1);
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
