package repository;

/**
 * Created by Dragos on 3/15/2017.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightRepo
{
    private List<Flight> flights = new ArrayList<Flight>();

    private Connection connection = null;
    private Statement statement;


    public FlightRepo() throws SQLException {

        try {
            connection = DriverManager.getConnection("JDBC:mysql://localhost:8080/flights", "root", "");
            statement = connection.createStatement();
        }
        catch(SQLException se)

        {
            se.printStackTrace();
        }
        String query = "select * from flight";
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
    @Bean
    public List<Flight> getAll()
    {
        return flights;
    }


    @Bean
    public Flight findByDestinationAndDate(String dest, Date data)
    {
        for(Flight flight : flights)
        {
            if(flight.getDestination().equals(dest) && flight.getDatehour().equals(data))
                return flight;
        }
        Flight f = new Flight(0,"","",0,null);
        return f;

    }

    @Bean
    public void updateFlight(Flight flight) throws SQLException
    {
        for (Flight f: flights)
        {
            if(flight.getDestination().equals(f.getDestination()) && flight.getDatehour().equals(f.getDatehour())) {
                f.setFreeseats(flight.getFreeseats());
                String destination = flight.getDestination();
                String q = "update \"Flights\" set  \"Destination\"='" + flight.getDestination() + "',\"Airport\"='" + flight.getAirport() + "', \"FreeSeats\"='" + flight.getFreeseats() + "' where \"Id\"='" + f.getFlightId() + "'";
                statement.executeUpdate(q);
            }
        }
    }

}
