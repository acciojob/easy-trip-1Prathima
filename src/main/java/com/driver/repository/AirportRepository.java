package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    public HashMap<Integer, Passenger> passengerDb = new HashMap<>();
    public HashMap<String, Airport> airportDb = new HashMap<>();
    public HashMap<Integer, Flight> flightDb = new HashMap<>();
    public HashMap<Integer, List<Integer>> flightPassengerDb = new HashMap<>();

    public void addAirport(Airport airport) {
        airportDb.put(airport.getAirportName(), airport);
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
                if(flight.getDuration() < ans){
                    ans = flight.getDuration();
                }
            }
        }
        if(ans == Integer.MAX_VALUE){
            return -1;
        }
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int count = 0;
//        Airport airport = airportDb.get(airportName);
//        if(Objects.isNull(airport)){
//            return 0;
//        }
//        City city = airport.getCity();
//        int count = 0;
//
//        for(Flight flight:flightDb.values()){
//            if(date.equals(flight.getFlightDate()))
//                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){
//
//                    int flightId = flight.getFlightId();
//                    count = count + flightToPassengerDb.get(flightId).size();
//                }
//        }


        if(airportDb.containsKey(airportName)) {
            Airport airport = airportDb.get(airportName);
            City city = airport.getCity();
            for(Flight flight : flightDb.values()){
                if(flight.getFlightDate() == date){
                    if(flight.getFromCity() == city || flight.getToCity() == city){
                        int flightId = flight.getFlightId();
                        count = count + flightPassengerDb.get(flightId).size();
                    }
                }
            }
//            for (int flightId : flightPassengerDb.keySet()) {
//                if (flightDb.get(flightId).getFlightDate() == date && (flightDb.get(flightId).getFromCity() == airportDb.get(airportName).getCity()
//                        || flightDb.get(flightId).getToCity() == airportDb.get(airportName).getCity())) {
//                    count += flightPassengerDb.get(flightId).size();
//                }
//            }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int ans = 0;
        if(flightPassengerDb.containsKey(flightId)) {
            ans = 3000 + (flightPassengerDb.get(flightId).size() * 50);
        }
        else{
            ans = 3000;
        }
        return ans;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(flightPassengerDb.containsKey(flightId)) {
            if (flightPassengerDb.get(flightId).size() >= flightDb.get(flightId).getMaxCapacity()) {
                return "FAILURE";
            }
        }
        List<Integer> passengers = flightPassengerDb.get(flightId);
        if (passengers != null && passengers.contains(passengerId)) {
            return "FAILURE";
        }
        else {
            if(passengers == null){
                passengers = new ArrayList<>();
            }
            passengers.add(passengerId);
            flightPassengerDb.put(flightId, passengers);
            return "SUCCESS";
        }
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

    public void addFlight(Flight flight) {
        flightDb.put(flight.getFlightId(), flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        String ans = null;
        if(flightDb.containsKey(flightId)) {
            City city = flightDb.get(flightId).getFromCity();
            for (Map.Entry<String, Airport> entry : airportDb.entrySet()) {
                if (entry.getValue().getCity() == city) {
                    ans = entry.getKey();
                }
            }
        }
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        int bookedSeat = flightPassengerDb.get(flightId).size();
        int perSeatRevenue = 3000 * bookedSeat;
        int totalRevenue = (bookedSeat * (bookedSeat+1) * 25) + (perSeatRevenue);
        if(bookedSeat == 1){
            return 3000;
        }
        return totalRevenue;
    }

    public void addPassenger(Passenger passenger) {
        passengerDb.put(passenger.getPassengerId(), passenger);
    }
}

