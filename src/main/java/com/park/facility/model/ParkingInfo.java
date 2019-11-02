package com.park.facility.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Parking info object contains more information of parking
 *
 * @author Imad Berkati
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingInfo {

    /**
     * Parking id
     */
    @JsonProperty("id")
    private String id;

    /**
     * Parking name
     */
    @JsonProperty("key")
    private String name;

    /**
     * Parking status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Parking places capacity
     */
    @JsonProperty("max")
    private Integer capacity;

    /**
     * number of free places
     */
    @JsonProperty("free")
    private Integer freePlaces;

    /**
     * List of coordinates
     * Note that the first element is the latitude and the second element is the longitude
     */
    @JsonProperty("geo")
    private List<Double> coordinates;

    /**
     * parking schedule description
     */
    @JsonProperty("orgahoraires")
    private String scheduleDescription;

    public ParkingInfo() {

    }

    public ParkingInfo(String id, String name, String status, Integer capacity, Integer freePlaces,
                       List<Double> coordinates, String scheduleDescription) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.capacity = capacity;
        this.freePlaces = freePlaces;
        this.coordinates = coordinates;
        this.scheduleDescription = scheduleDescription;
    }

//TODO Add Prices

}
