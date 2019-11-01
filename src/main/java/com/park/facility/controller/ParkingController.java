package com.park.facility.controller;

import com.park.facility.model.ApiMessage;
import com.park.facility.model.ParkingResponse;
import com.park.facility.service.IParkingService;
import com.park.facility.utils.ParkingConstants;
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

//TODO Add JavaDoc

@RestController
@RequestMapping("/api/v1/car_parks")
public class ParkingController {

    @Autowired
    private IParkingService parkingService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ParkingResponse> getAll() {
        ParkingResponse parkingResponse;
        try {
            parkingResponse = parkingService.getAll();
            return ResponseEntity.ok().body(parkingResponse);
        } catch (Exception ex) {
            parkingResponse = new ParkingResponse();
            parkingResponse.addError(new ApiMessage("CAR_PARKS_URL_GET", "Error in API: " + ParkingConstants.CAR_PARKS_URL_GET));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(parkingResponse);
        }
    }

    @RequestMapping(value = "/zone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ParkingResponse> getByZone(@RequestParam String latitude,
                                                     @RequestParam String longitude,
                                                     @RequestParam String distance) {
        ParkingResponse parkingResponse;
        try {
            parkingResponse = parkingService.getByZone(Double.valueOf(latitude), Double.valueOf(longitude),
                    Double.valueOf(distance));
            if (parkingResponse != null && CollectionUtils.isEmpty(parkingResponse.getParks())) {
                parkingResponse.addWarning(new ApiMessage("EMPTY_DATA", "There is no parks near to you," +
                        " please update your zone distance"));
            }
            return ResponseEntity.ok().body(parkingResponse);
        } catch (NumberFormatException ex) {
            parkingResponse = new ParkingResponse();
            parkingResponse.addError(new ApiMessage("ZONE_PARAMETERS", "latitude, longitude and distance" +
                    " parameters must be numbers"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parkingResponse);
        } catch (Exception ex) {
            parkingResponse = new ParkingResponse();
            parkingResponse.addError(new ApiMessage("CAR_PARKS_BY_ZONE_URL_GET", "Error in API: "
                    + ParkingConstants.CAR_PARKS_URL_GET));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(parkingResponse);
        }
    }
}
