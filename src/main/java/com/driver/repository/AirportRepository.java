package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AirportRepository {

    HashMap<Integer, Passenger> passengerDb = new HashMap<>();
    HashMap<String, Airport> airportDb = new HashMap<>();
    HashMap<Integer, Flight> flightDb = new HashMap<>();
    HashMap<Integer, List<Integer>> flightPassengerDb = new HashMap<>();

    public void addAirport(Airport airport) {
        airportDb.put(airport.getAirportName(), airport);
    }

    public void addFlight(Flight flight) {
        flightDb.put(flight.getFlightId(), flight);
    }

    public void addPassenger(Passenger passenger) {
        passengerDb.put(passenger.getPassengerId(), passenger);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if (flightPassengerDb.get(flightId).size() >= flightDb.get(flightId).getMaxCapacity()) {
            return "FAILURE";
        }
        List<Integer> passengers = flightPassengerDb.get(flightId);
        if (passengers.contains(passengerId)) {
            return "FAILURE";
        } else {
            passengers.add(passengerId);
            flightPassengerDb.put(flightId, passengers);
            return "SUCCESS";
        }
    }

    public String getLargestAirportName() {
        int max = 0;
        String ans = "";
        for (Airport airport : airportDb.values()) {
            if (airport.getNoOfTerminals() > max) {
                max = airport.getNoOfTerminals();
                ans = airport.getAirportName();
            }
        }

        for (Airport airport : airportDb.values()) {
            if (airport.getNoOfTerminals() == max && airport.getAirportName() != ans) {
                if (airport.getAirportName().compareTo(ans) < 0) {
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = Integer.MAX_VALUE;
        for (Flight flight : flightDb.values()) {
            if (flight.getFromCity() == fromCity && flight.getToCity() == toCity) {
                ans = Math.min(ans, flight.getDuration());
            }
        }
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int count = 0;
        for (int flightId : flightPassengerDb.keySet()) {
            if (flightDb.get(flightId).getFlightDate() == date && (flightDb.get(flightId).getFromCity() == airportDb.get(airportName).getCity()
                    || flightDb.get(flightId).getToCity() == airportDb.get(airportName).getCity())) {
                count += flightPassengerDb.get(flightId).size();
            }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int ans = 3000 + (flightPassengerDb.get(flightId).size() * 50);
        return ans;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if (flightPassengerDb.containsKey(flightId)) {
            List<Integer> passengers = flightPassengerDb.get(flightId);
            if (passengers.contains(passengerId)) {
                passengers.remove(passengerId);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        int count = 0;
        for(List<Integer> passengers : flightPassengerDb.values()){
            if(passengers.contains(passengerId)){
                count++;
            }
        }
        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId){
        String ans = null;
        City city = flightDb.get(flightId).getFromCity();
        for(Map.Entry<String, Airport> entry : airportDb.entrySet()){
            if(entry.getValue().getCity() == city){
                ans = entry.getKey();
            }
        }
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        int bookedSeat = flightPassengerDb.get(flightId).size();
        int availableSeat = flightDb.get(flightId).getMaxCapacity() - bookedSeat;
        int perSeatRevenue = (bookedSeat / availableSeat);
        int totalRevenue = bookedSeat * (perSeatRevenue);
        return totalRevenue;
    }
}