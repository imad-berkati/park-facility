package com.park.facility.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Parking object
 *
 * @author Imad Berkati
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parking implements Comparable<Parking> {

    /**
     * Record id
     */
    @JsonProperty("recordid")
    private String recordId;

    /**
     * {@link ParkingInfo} parking info
     */
    @JsonProperty("fields")
    private ParkingInfo info;

    /**
     * Api last update date
     */
    @JsonProperty("record_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    private Date lastUpdate;

    /**
     * distance between user and car park
     * Note that this field is used if and only if we get car parks by zone
     */
    @JsonProperty("distance")
    private Double distance;

    /**
     * The default compare is by free places
     * Note that the compareTo can compare also by distance if it's not null
     */
    @Override
    public int compareTo(Parking o) {
        int result = 0;
        if (this.getInfo() != null && o.getInfo() != null && this.getInfo().getFreePlaces() != null
                && o.getInfo().getFreePlaces() != null) {
            result = o.getInfo().getFreePlaces() - this.getInfo().getFreePlaces();
        }
        if (this.distance != null && o.distance != null) {
            result = this.getDistance().intValue() - o.getDistance().intValue();
        }
        return result;
    }
}
