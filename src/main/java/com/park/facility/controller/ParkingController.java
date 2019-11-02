package com.park.facility.controller;

import com.park.facility.model.ApiMessage;
import com.park.facility.model.ParkingResponse;
import com.park.facility.service.IParkingService;
import com.park.facility.util.ParkingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Parking controller is the entry point of all external calls
 *
 * @author Imad Berkati
 */

@RestController
@RequestMapping("/api/v1/car_parks")
public class ParkingController {

    @Autowired
    private IParkingService parkingService;

    /**
     * Display all car parks
     *
     * @return {@link ParkingResponse} contains the list of car parks
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ParkingResponse> getAvailableCarParks() {
        ParkingResponse parkingResponse;
        try {
            parkingResponse = parkingService.getAvailableCarParks();
            if (parkingResponse != null && CollectionUtils.isEmpty(parkingResponse.getParks())) {
                // Add warning if parks list is empty
                parkingResponse.addWarning(new ApiMessage("EMPTY_DATA", "There is no available parks," +
                        " please try later."));
            }
            return ResponseEntity.ok().body(parkingResponse);
        } catch (Exception ex) {
            parkingResponse = new ParkingResponse();
            parkingResponse.addError(new ApiMessage("CAR_PARKS_URL_GET", "Error in API: " + ParkingConstants.CAR_PARKS_URL_GET));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(parkingResponse);
        }
    }

    /**
     * Display all car parks by zone
     * <p>
     *
     * @param latitude  user location latitude
     * @param longitude user location longitude
     * @param distance  latitude of the point 2
     * @return {@link ParkingResponse} contains the list of car parks, sorted by the distance between user and car parks
     */
    @RequestMapping(value = "/zone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ParkingResponse> getCarParksByZone(@RequestParam String latitude,
                                                             @RequestParam String longitude,
                                                             @RequestParam String distance) {
        ParkingResponse parkingResponse;
        try {
            parkingResponse = parkingService.getCarParksByZone(Double.valueOf(latitude), Double.valueOf(longitude),
                    Double.valueOf(distance));
            if (parkingResponse != null && CollectionUtils.isEmpty(parkingResponse.getParks())) {
                // Add warning if parks list is empty
                parkingResponse.addWarning(new ApiMessage("EMPTY_DATA", "There is no parks near to you," +
                        " please update your zone distance"));
            }
            return ResponseEntity.ok().body(parkingResponse);
        } catch (NumberFormatException ex) {
            parkingResponse = new ParkingResponse();
            // Add bad parameters error
            parkingResponse.addError(new ApiMessage("BAD_PARAMETER", "latitude, longitude and distance" +
                    " parameters must be numbers"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parkingResponse);
        } catch (Exception ex) {
            // Add bad request error
            parkingResponse = new ParkingResponse();
            parkingResponse.addError(new ApiMessage("CAR_PARKS_BY_ZONE_URL_GET", "Error in API: "
                    + ParkingConstants.CAR_PARKS_URL_GET));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parkingResponse);
        }
    }
}
