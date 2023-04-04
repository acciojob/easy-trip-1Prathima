package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class AirportService {
    public AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName(){
        String ans = airportRepository.getLargestAirportName();
        return ans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double ans = airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        int ans = airportRepository.getNumberOfPeopleOn(date, airportName);
        return ans;
    }

    public int calculateFlightFare(Integer flightId){
        int ans = airportRepository.calculateFlightFare(flightId);
        return ans;
    }

    public String bookATicket(Integer flightId, Integer passengerId){
        String ans = airportRepository.bookATicket(flightId, passengerId);
        return ans;
    }

    public String cancelATicket(Integer flightId, Integer passengerId){
        String ans = airportRepository.cancelATicket(flightId, passengerId);
        return ans;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        int ans = airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
        return ans;
    }

    public void addFlight(Flight flight){
        airportRepository.addFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        String ans = airportRepository.getAirportNameFromFlightId(flightId);
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        int ans = airportRepository.calculateRevenueOfAFlight(flightId);
        return ans;
    }

    public void addPassenger(Passenger passenger){
        airportRepository.addPassenger(passenger);
    }
}

