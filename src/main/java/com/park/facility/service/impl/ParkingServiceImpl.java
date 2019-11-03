package com.park.facility.service.impl;

import com.park.facility.model.Parking;
import com.park.facility.model.ParkingResponse;
import com.park.facility.service.IParkingService;
import com.park.facility.util.ParkingConstants;
import com.park.facility.util.ParkingUtils;
import com.park.facility.util.enums.DistanceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * Implementation of the {@link IParkingService} interface
 *
 * @author Imad Berkati
 */
@Service
public class ParkingServiceImpl implements IParkingService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Retrieve all available car parks by doing a GET request on the {@literal Rennes car parks} API.
     * <p>
     * Note that this method call the {@code getForObject} method from {@link RestTemplate} class to retrieve the JSON
     * response from the API and the mapping with {@link ParkingResponse} object
     *
     * @return {@link ParkingResponse} contains the list of available car parks sorted by number of available spaces
     */
    @Override
    public ParkingResponse getAvailableCarParks() {
        ParkingResponse parkingResponse = restTemplate.getForObject(ParkingConstants.CAR_PARKS_URL_GET,
                ParkingResponse.class);
        if (parkingResponse != null && !CollectionUtils.isEmpty(parkingResponse.getParks())) {
            // filter available car parks
            TreeSet<Parking> filteredCarParks = ParkingUtils.filterAvailableCarParks(parkingResponse.getParks());
            parkingResponse.setParks(filteredCarParks);
            parkingResponse.setParksCount(parkingResponse.getParks().size());
        }
        return parkingResponse;
    }

    /**
     * Retrieve all available car parks by zone (latitude, longitude and distance) doing a GET request on the
     * {@literal Rennes car parks} API using {@code geofilter.distance} query parameter.
     * <p>
     * The zone is represented as a circle, The latitude and longitude are the location center and the distance
     * is the radius
     * <p>
     * Note that this method call the {@code getForObject} method from {@link RestTemplate} class to get the JSON
     * response from the API and do the mapping with {@link ParkingResponse} object.
     *
     * @param latitude  user location latitude
     * @param longitude user location longitude
     * @param distance  user limit distance
     * @return {@link ParkingResponse} contains the list of available car parks, sorted by distance
     */
    @Override
    public ParkingResponse getCarParksByZone(Double latitude, Double longitude, Double distance) {
        // build the HashMap parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("geoDistanceParam", latitude + "," + longitude + "," + distance);
        ParkingResponse parkingResponse = restTemplate.getForObject(ParkingConstants.CAR_PARKS_BY_ZONE_URL_GET,
                ParkingResponse.class, parameters);

        if (parkingResponse != null && !CollectionUtils.isEmpty(parkingResponse.getParks())) {
            // filter available car parks
            TreeSet<Parking> filteredCarParks = ParkingUtils.filterAvailableCarParks(parkingResponse.getParks());
            parkingResponse.setParks(filteredCarParks);
            parkingResponse.setParksCount(parkingResponse.getParks().size());

            // Set the distance between user and car parks
            Predicate<Parking> coordinatesPredicate = (parking -> !CollectionUtils.isEmpty(parking.getInfo().getCoordinates()) &&
                    parking.getInfo().getCoordinates().size() == 2);
            parkingResponse.getParks().stream().filter(coordinatesPredicate).forEach(parking -> parking.setDistance(ParkingUtils
                    .getDistanceBetweenTwoPoints(latitude, longitude, parking.getInfo().getCoordinates().get(0),
                            parking.getInfo().getCoordinates().get(1), DistanceUnit.METER)));

            // Sort parks by distance
            TreeSet<Parking> sortedCarParks = new TreeSet<>();
            parkingResponse.getParks().forEach(parking -> sortedCarParks.add(parking));
            parkingResponse.setParks(sortedCarParks);
        }

        return parkingResponse;
    }

    /**
     * Inject the default RestTemplateBuilder bean provided by Spring Boot
     *
     * @param restTemplateBuilder the injected bean
     * @return {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
