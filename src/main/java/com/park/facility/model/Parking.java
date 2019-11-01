package com.park.facility.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parking {

    @JsonProperty("recordid")
    private String recordId;

    @JsonProperty("fields")
    private ParkingInfo info;

    @JsonProperty("record_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    private Date lastUpdate;

    @JsonProperty("distance")
    private Double distance;
}
