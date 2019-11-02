package com.park.facility.service;

import com.park.facility.model.ParkingResponse;

/**
 * This interface represent the parking service
 *
 * Note that each class implements this interface must {@code Override} all abstracts functions
 *
 * @author Imad Berkati
 */
public interface IParkingService {

    ParkingResponse getAvailableCarParks();

    ParkingResponse getCarParksByZone(Double latitude, Double longitude, Double distance);
}
