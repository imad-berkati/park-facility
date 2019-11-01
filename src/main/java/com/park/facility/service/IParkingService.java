package com.park.facility.service;

import com.park.facility.model.ParkingResponse;

public interface IParkingService {
    ParkingResponse getAll();
    ParkingResponse getByZone(Double latitude, Double longitude, Double distance);

}
