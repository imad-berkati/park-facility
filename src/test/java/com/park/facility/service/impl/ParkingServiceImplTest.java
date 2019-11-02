package com.park.facility.service.impl;

import com.park.facility.model.Parking;
import com.park.facility.model.ParkingInfo;
import com.park.facility.model.ParkingResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

public class ParkingServiceImplTest {

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Mock
    private RestTemplate restTemplate;

    private ParkingResponse mockedParkingResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Parking gareSudParking = new Parking();
        gareSudParking.setLastUpdate(new Date());
        gareSudParking.setRecordId("ad9209d6fa7d856980bd9148abcaa0b2db9d0318");
        gareSudParking.setInfo(new ParkingInfo("477", "Gare-Sud", "OUVERT", 964, 33,
                Arrays.asList(48.10623522, -1.676050081), "24h/24 et 7j/7"));

        Parking charlesDeGaulleParking = new Parking();
        charlesDeGaulleParking.setLastUpdate(new Date());
        charlesDeGaulleParking.setRecordId("ca59891c20f0a15267d072d9ca9a2bf488063d86");
        charlesDeGaulleParking.setInfo(new ParkingInfo("1815", "Charles-de-gaulle", "OUVERT", 756, 11,
                Arrays.asList(48.1025360816, -1.672518275), "7j/7"));

        Parking colombierParking = new Parking();
        colombierParking.setLastUpdate(new Date());
        colombierParking.setRecordId("3d5878b8b18ed96b72f18a24484aaec776b82e92");
        colombierParking.setInfo(new ParkingInfo("310", "Colombier", "OUVERT", 1115, 0,
                Arrays.asList(48.10476252, -1.678624547), "24h/24 et 7j/7"));

        TreeSet<Parking> carParks = new TreeSet<>();
        carParks.add(charlesDeGaulleParking);
        carParks.add(gareSudParking);
        carParks.add(colombierParking);

        mockedParkingResponse = new ParkingResponse();
        mockedParkingResponse.setParksCount(3);
        mockedParkingResponse.setParks(carParks);
    }

    @Test
    public void getAvailableCarParks() {
        Mockito.when(restTemplate.getForObject(anyString(), eq(ParkingResponse.class))).thenReturn(mockedParkingResponse);
        ParkingResponse parkingResponse = parkingService.getAvailableCarParks();
        assertThat(parkingResponse, notNullValue());
        assertThat(parkingResponse.getErrors(), nullValue());
        assertThat(parkingResponse.getParksCount(), comparesEqualTo(2));
        assertThat(parkingResponse.getParks(), hasSize(2));
        assertThat(parkingResponse.getParks().first().getInfo().getFreePlaces(), greaterThan(parkingResponse.getParks()
                .last().getInfo().getFreePlaces()));
        parkingResponse.getParks().forEach(parking -> assertThat(parking.getDistance(), nullValue()));
    }

    @Test
    public void getCarParksByZone() {
        Mockito.when(restTemplate.getForObject(anyString(), eq(ParkingResponse.class), anyMap())).thenReturn(mockedParkingResponse);
        ParkingResponse parkingResponse = parkingService.getCarParksByZone(48.1034827, -1.6722622, 500d);
        assertThat(parkingResponse, notNullValue());
        assertThat(parkingResponse.getErrors(), nullValue());
        assertThat(parkingResponse.getParksCount(), comparesEqualTo(2));
        assertThat(parkingResponse.getParks(), hasSize(2));
        assertThat(parkingResponse.getParks().last().getDistance(), greaterThan(parkingResponse.getParks()
                .first().getDistance()));
        parkingResponse.getParks().forEach(parking -> assertThat(parking.getDistance(), lessThanOrEqualTo(500d)));
    }

}
