package com.couchbase.client.sample;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ingenthr on 6/27/16.
 */
public class TravelInformationRepositoryTest {
    static TravelInformationRepository travelRepository;

    @BeforeClass
    public static void init() {
        //TODO: remove this construction
        travelRepository = new TravelInformationRepository();
        travelRepository.buildClient();
    }

    @Test
    public void testFindAirlineById() throws Exception {
        Airline it = travelRepository.findAirlineById("airline_5209");
        System.out.println(it);
        assertNotNull(it);
    }

    @Test
    public void testFindAirlineByName() {
        Airline it = travelRepository.findFirstAirlineByName("United");
        System.out.println(it);
    }

    @Test
    public void testFindAirports() {
        Airline it = travelRepository.findFirstAirlineByName("United");
        List<Airport> airports = travelRepository.findAirports(it);
        System.err.println(airports);


    }

    @Test
    public void testFindAirportsForAirline() {
//        System.out.println(travelRepository.findAirportsForAirline(" "));
        System.out.println(travelRepository.findAirportsForAirline(""));
        System.out.println(travelRepository.findAirportsForAirline("United"));
    }
}