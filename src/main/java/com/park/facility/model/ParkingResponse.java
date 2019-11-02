package com.park.facility.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * This parking response object
 *
 * @author Imad Berkati
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingResponse {

    /**
     * list of errors
     */
    private List<ApiMessage> errors;

    /**
     * list of warnings
     */
    private List<ApiMessage> warnings;

    /**
     * number of parks in the response
     */
    @JsonProperty("nhits")
    private Integer parksCount;

    /**
     * TreeSet of {@link Parking}
     */
    @JsonProperty("records")
    private TreeSet<Parking> parks;

    /**
     * Add error in errors list
     *
     * @param error the new error
     */
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
    /**
     * Add warning in warnings {@code list}
     *
     * @param warning the new error
     */
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
