package service;

import Observer.Observable;
import model.Flight;
import repository.FlightRepo;
import Observer.Observer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Dragos on 3/21/2017.
 */
public class FlightService implements Observable<Flight>
{
    private FlightRepo repo;
    protected List <Observer<Flight>> observers = new ArrayList<Observer<Flight>>();

    public FlightService(FlightRepo frep)
    {
        this.repo = frep;
    }

    public FlightService()
    {

    }

    public List<Flight> getAllFlights()
    {
        List<Flight> s = new ArrayList<Flight>();
        Iterable<Flight> list=repo.getAll();
        for (Flight f:list)
            s.add(f);
        notifyObservers();
        return s;

    }

    public int buyFlight(Flight f)
    {
        repo.updateFlight(f);
        Flight cop = repo.findById(f.getFlightId());
        if (cop.getFreeseats() == f.getFreeseats() - 1)
        {
            notifyObservers();
            return 1;
        }
        else
            return 0;

    }

    public void deleteFlight(Flight c)
    {
        repo.deleteFlight(c.getFlightId());
        if (repo.findById(c.getFlightId()) == null)
            notifyObservers();
    }
    public Flight findByDestination(String dest,Date dat)
    {
        Flight ret = repo.findByDestinationAndDate(dest,dat);
        if (ret != null)
            notifyObservers();
        return ret;
    }


    public void addObserver(Observer<Flight> o)
    {
        observers.add(o);
    }

    public void removeObserver(Observer<Flight> o)
    {
        observers.remove(o);
    }

    public void notifyObservers()
    {
        for(Observer<Flight> o : observers)
        {
            o.update(this);
        }
    }
}
