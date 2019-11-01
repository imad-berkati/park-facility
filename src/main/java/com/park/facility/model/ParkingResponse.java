package com.park.facility.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingResponse {

    private List<ApiMessage> errors;

    private List<ApiMessage> warnings;

    @JsonProperty("nhits")
    private Integer parksCount;

    @JsonProperty("records")
    private List<Parking> parks;

    public void addError(ApiMessage error) {
        if (error != null && !StringUtils.isEmpty(error.getCode()) && !StringUtils.isEmpty(error.getMessage())) {
            if (CollectionUtils.isEmpty(this.errors)) {
                errors = new ArrayList<>();
                errors.add(error);
            } else {
                this.getErrors().add(error);
            }
        }
    }

    public void addWarning(ApiMessage warning) {
        if (warning != null && !StringUtils.isEmpty(warning.getCode()) && !StringUtils.isEmpty(warning.getMessage())) {
            if (CollectionUtils.isEmpty(this.warnings)) {
                warnings = new ArrayList<>();
                warnings.add(warning);
            } else {
                this.getWarnings().add(warning);
            }
        }
    }

}
