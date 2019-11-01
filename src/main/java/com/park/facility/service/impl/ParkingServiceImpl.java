package com.park.facility.service.impl;

import com.park.facility.model.Parking;
import com.park.facility.model.ParkingResponse;
import com.park.facility.service.IParkingService;
import com.park.facility.utils.ParkingConstants;
import com.park.facility.utils.ParkingUtils;
import com.park.facility.utils.enums.DistanceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

//TODO Add JavaDoc
@Service
public class ParkingServiceImpl implements IParkingService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ParkingResponse getAll() {
        return restTemplate.getForObject(ParkingConstants.CAR_PARKS_URL_GET,
                ParkingResponse.class);
    }

    @Override
    public ParkingResponse getByZone(Double latitude, Double longitude, Double distance) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("geoDistanceParam", latitude + "," + longitude + "," + distance);
        ParkingResponse parkingResponseEntity = restTemplate.getForObject(ParkingConstants.CAR_PARKS_BY_ZONE_URL_GET,
                ParkingResponse.class, parameters);

        if (parkingResponseEntity != null && !CollectionUtils.isEmpty(parkingResponseEntity.getParks())) {
            Predicate<Parking> parkingPredicate = (parking -> parking != null && parking.getInfo() != null
                    && !CollectionUtils.isEmpty(parking.getInfo().getCoordinates()) && parking.getInfo().getCoordinates().size() == 2);

            parkingResponseEntity.getParks().stream().filter(parkingPredicate).forEach(parking -> parking.setDistance(ParkingUtils
                    .getDistanceBetweenTwoPoints(latitude, longitude, parking.getInfo().getCoordinates().get(0),
                            parking.getInfo().getCoordinates().get(1), DistanceUnit.METER)));
        }

        return parkingResponseEntity;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
