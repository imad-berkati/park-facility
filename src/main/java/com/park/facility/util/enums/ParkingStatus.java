package com.park.facility.util.enums;

/**
 * Parking status enum
 *
 * @author Imad Berkati
 */
public enum ParkingStatus {
    OPENED("OUVERT"), CLOSED("FERME");

    private String status;

    ParkingStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
