package com.park.facility.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingInfo {

    @JsonProperty("id")
    private String id;

    @JsonProperty("key")
    private String name;

    @JsonProperty("status")
    private String status;

    @JsonProperty("max")
    private Integer capacity;

    private Integer freePlaces;

    @JsonProperty("geo")
    private List<Double> coordinates;

    @JsonProperty("orgahoraires")
    private String scheduleDescription;

    @JsonProperty("freePlaces")
    public Integer getFreePlaces() {
        return freePlaces;
    }
    @JsonProperty("free")
    public void setFreePlaces(Integer freePlaces) {
        this.freePlaces = freePlaces;
    }
//TODO Add Prices
}
