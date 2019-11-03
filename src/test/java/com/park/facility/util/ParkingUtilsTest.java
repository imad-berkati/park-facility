package com.park.facility.util;

import com.park.facility.model.Parking;
import com.park.facility.model.ParkingInfo;
import com.park.facility.util.enums.DistanceUnit;
import com.park.facility.util.enums.ParkingStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author Imad Berkati
 */
public class ParkingUtilsTest {

    private Parking park1;
    private Parking park2;
    private Parking park3;

    @Before
    public void setup() {

        ParkingInfo parkingInfo1 = new ParkingInfo();
        parkingInfo1.setFreePlaces(0);
        parkingInfo1.setStatus(ParkingStatus.OPENED.toString());
        park1 = new Parking();
        park1.setInfo(parkingInfo1);
        ParkingInfo parkingInfo2 = new ParkingInfo();
        parkingInfo2.setFreePlaces(35);
        parkingInfo2.setStatus(ParkingStatus.OPENED.toString());
        park2 = new Parking();
        park2.setInfo(parkingInfo2);
        park3 = new Parking();
    }

    @Test
    public void getDistanceBetweenTwoPoints() {
        double distance = ParkingUtils.getDistanceBetweenTwoPoints(ParkingConstants.RENNES_TRAIN_STATION_LATITUDE,
                ParkingConstants.RENNES_TRAIN_STATION_LONGITUDE, ParkingConstants.KLEBER_PARK_LATITUDE,
                ParkingConstants.KLEBER_PARK_LONGITUDE, DistanceUnit.METER);
        assertThat(distance, comparesEqualTo((double) ParkingConstants.DISTANCE_BETWEEN_KLEBER_AND_TRAIN_STATION.intValue()));
    }

    @Test
    public void degToRad() {
        assertThat(ParkingUtils.degToRad(100d), comparesEqualTo(1.7453292519943295));
    }

    @Test
    public void radToDeg() {
        assertThat(ParkingUtils.radToDeg(100d), comparesEqualTo(5729.5779513082325));
    }

    @Test
    public void roundOffTo2DecPlaces() {
        assertThat(ParkingUtils.roundOffTo2DecPlaces(5729.5779513082325), comparesEqualTo(5729.58));
    }

    @Test
    public void filterAvailableCarParks() {
        TreeSet<Parking> parks = new TreeSet<>();
        parks.add(park1);
        parks.add(park2);
        parks.add(park3);
        TreeSet<Parking> filteredParks = ParkingUtils.filterAvailableCarParks(parks);
        assertThat(filteredParks, hasSize(1));
        assertThat(filteredParks.first().getInfo().getFreePlaces(), greaterThan(0));
        assertThat(filteredParks.first().getInfo().getStatus(), equalTo(ParkingStatus.OPENED.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDistanceBetweenTwoPointsLatitude1Exception() {
        ParkingUtils.getDistanceBetweenTwoPoints(null, ParkingConstants.RENNES_TRAIN_STATION_LONGITUDE,
                ParkingConstants.KLEBER_PARK_LATITUDE, ParkingConstants.KLEBER_PARK_LONGITUDE, DistanceUnit.METER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDistanceBetweenTwoPointsLongitude1Exception() {
        ParkingUtils.getDistanceBetweenTwoPoints(ParkingConstants.RENNES_TRAIN_STATION_LATITUDE, null,
                ParkingConstants.KLEBER_PARK_LATITUDE, ParkingConstants.KLEBER_PARK_LONGITUDE, DistanceUnit.METER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDistanceBetweenTwoPointsLatitude2Exception() {
        ParkingUtils.getDistanceBetweenTwoPoints(ParkingConstants.RENNES_TRAIN_STATION_LATITUDE, ParkingConstants.RENNES_TRAIN_STATION_LONGITUDE,
                null, ParkingConstants.KLEBER_PARK_LONGITUDE, DistanceUnit.METER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDistanceBetweenTwoPointsLongitude2Exception() {
        ParkingUtils.getDistanceBetweenTwoPoints(ParkingConstants.RENNES_TRAIN_STATION_LATITUDE, ParkingConstants.RENNES_TRAIN_STATION_LONGITUDE,
                ParkingConstants.KLEBER_PARK_LATITUDE, null, DistanceUnit.METER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDistanceBetweenTwoPointsUnitException() {
        ParkingUtils.getDistanceBetweenTwoPoints(ParkingConstants.RENNES_TRAIN_STATION_LATITUDE, ParkingConstants.RENNES_TRAIN_STATION_LONGITUDE,
                ParkingConstants.KLEBER_PARK_LATITUDE, ParkingConstants.KLEBER_PARK_LONGITUDE, DistanceUnit.DEGREE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void degToRadException() {
        ParkingUtils.degToRad(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void radToDegException() {
        ParkingUtils.radToDeg(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void roundOffTo2DecPlacesException() {
        ParkingUtils.roundOffTo2DecPlaces(null);
    }
}
