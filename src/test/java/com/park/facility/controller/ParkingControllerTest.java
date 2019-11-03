package com.park.facility.controller;

import com.park.facility.ParkFacilityApplication;
import com.park.facility.model.ApiMessage;
import com.park.facility.model.ParkingResponse;
import com.park.facility.util.enums.ParkingStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * @author Imad Berkati
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkFacilityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getAvailableCarParks() {
        ResponseEntity<ParkingResponse> responseEntity = restTemplate.getForEntity(buildURLWithPort("/api/v1/car_parks/"),
                ParkingResponse.class);
        sharedTests(responseEntity);
    }

    @Test
    public void getCarParksByZone() {
        ResponseEntity<ParkingResponse> responseEntity = restTemplate.getForEntity(
                buildURLWithPort("/api/v1/car_parks/zone?latitude=48.104160&longitude=-1.672072&distance=500"),
                ParkingResponse.class);
        sharedTests(responseEntity);
        ParkingResponse parkingResponse = responseEntity.getBody();
        // distances test
        parkingResponse.getParks().forEach(parking -> assertThat(parking.getDistance(),
                lessThanOrEqualTo(500d)));
    }

    @Test
    public void getCarParksByZoneEmptyResponse() {
        ResponseEntity<ParkingResponse> responseEntity = restTemplate.getForEntity(
                buildURLWithPort("/api/v1/car_parks/zone?latitude=48.104160&longitude=-1.672072&distance=10"),
                ParkingResponse.class);
        ParkingResponse parkingResponse = responseEntity.getBody();
        assertThat(responseEntity.getHeaders().get("Content-Type").get(0), equalTo("application/json"));
        assertThat(parkingResponse, notNullValue());
        assertThat(parkingResponse.getErrors(), nullValue());
        assertThat(parkingResponse.getWarnings(), notNullValue());
        assertThat(parkingResponse.getWarnings(), hasItem(new ApiMessage("EMPTY_DATA", "There is no parks near to you," +
                " please update your zone distance")));
        assertThat(parkingResponse.getParks(), notNullValue());
        assertThat(parkingResponse.getParks(), empty());

    }

    public void sharedTests(ResponseEntity<ParkingResponse> responseEntity) {
        assertThat(responseEntity, notNullValue());
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        assertThat(responseEntity.getHeaders().get("Content-Type").get(0), equalTo("application/json"));
        ParkingResponse parkingResponse = responseEntity.getBody();
        assertThat(parkingResponse.getErrors(), nullValue());
        assertThat(parkingResponse.getParks().stream().map(parking -> parking.getInfo().getStatus())
                .collect(Collectors.toList()), not(hasItems(ParkingStatus.CLOSED.toString())));
        assertThat(parkingResponse.getParks().stream().map(parking -> parking.getInfo().getFreePlaces())
                .collect(Collectors.toList()), not(hasItem(0)));
    }

    private String buildURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
