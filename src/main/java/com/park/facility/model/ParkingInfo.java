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
     * Parking spaces capacity
     */
    @JsonProperty("max")
    private Integer capacity;

    /**
     * number of available spaces
     */
    @JsonProperty("free")
    private Integer availableSpaces;

    /**
     * price for 15 minutes
     */
    @JsonProperty("tarif_15")
    private Double price0H15;

    /**
     * price for 30 minutes
     */
    @JsonProperty("tarif_30")
    private Double price0H30;

    /**
     * price for 1 hour
     */
    @JsonProperty("tarif_1h")
    private Double price1H00;

    /**
     * price for 1 hour and a half
     */
    @JsonProperty("tarif_1h30")
    private Double price1H30;

    /**
     * price for 2 hours
     */
    @JsonProperty("tarif_2h")
    private Double price2H00;

    /**
     * price for 3 hours
     */
    @JsonProperty("tarif_3h")
    private Double price3H00;

    /**
     * price for 4 hours
     */
    @JsonProperty("tarif_4h")
    private Double price4H00;

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

    public ParkingInfo(String id, String name, String status, Integer capacity, Integer availableSpaces, Double price0H15,
                       Double price0H30, Double price1H00, Double price1H30, Double price2H00, Double price3H00,
                       Double price4H00, List<Double> coordinates, String scheduleDescription) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.capacity = capacity;
        this.availableSpaces = availableSpaces;
        this.price0H15 = price0H15;
        this.price0H30 = price0H30;
        this.price1H00 = price1H00;
        this.price1H30 = price1H30;
        this.price2H00 = price2H00;
        this.price3H00 = price3H00;
        this.price4H00 = price4H00;
        this.coordinates = coordinates;
        this.scheduleDescription = scheduleDescription;
    }
}
