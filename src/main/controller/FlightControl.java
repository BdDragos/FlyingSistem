package controller;

/**
 * Created by Dragos on 3/16/2017.
 */

import repository.FlightRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightControl {

    private FlightRepo repo;

    public FlightControl()
    {

    }

    public FlightControl(FlightRepo repo)
    {
        this.repo = repo;
    }
}
