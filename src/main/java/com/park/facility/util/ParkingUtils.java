package com.park.facility.util;

import com.park.facility.model.Parking;
import com.park.facility.util.enums.DistanceUnit;
import org.springframework.util.CollectionUtils;

import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * This abstract class provides several GeoLocation utility methods
 *
 * @author Imad Berkati
 */
public abstract class ParkingUtils {

    /**
     * Calculate the distance between two points (lat,long)
     *
     * @param latitudePoint1  latitude of the point 1
     * @param longitudePoint1 longitude of the point 1
     * @param latitudePoint2  latitude of the point 2
     * @param longitudePoint2 longitude of the point 2
     * @param unit            {@link DistanceUnit} unit of returned distance
     * @return {@code double} distance between two locations
     * @throws IllegalArgumentException if one of parameters is null or unit parameter is out of enum scope
     */
    public static double getDistanceBetweenTwoPoints(Double latitudePoint1, Double longitudePoint1, Double latitudePoint2,
                                                     Double longitudePoint2, DistanceUnit unit) throws IllegalArgumentException {
        double theta, distance;
        if (longitudePoint1 == null || longitudePoint2 == null || unit == null) {
            throw new IllegalArgumentException("Null parameter");
        }
        theta = longitudePoint1 - longitudePoint2;
        distance = (Math.sin(degToRad(latitudePoint1)) * Math.sin(degToRad(latitudePoint2))) +
                (Math.cos(degToRad(latitudePoint1)) * Math.cos(degToRad(latitudePoint2)) * Math.cos(degToRad(theta)));
        distance = radToDeg(Math.acos(distance));
        switch (unit) {
            case KILOMETER:
                distance = roundOffTo2DecPlaces(distance * 60 * 1.1515 * 1.609344);
                break;
            case METER:
                distance = roundOffTo2DecPlaces(distance * 60 * 1.1515 * 1.609344 * 1000).intValue();
                break;
            default:
                throw new IllegalArgumentException("getDistanceBetweenTwoPoints : don't support the unit " + unit);
        }

        return distance;
    }

    /**
     * Filter available parks
     *
     * @param parks list of car parks
     * @return {@code TreeSet} available car parks
     */
    public static TreeSet<Parking> filterAvailableCarParks(TreeSet<Parking> parks) {
        if (!CollectionUtils.isEmpty(parks)) {
            Predicate<Parking> availableParksPredicate = (parking -> parking.getInfo() == null
                    || parking.getInfo().getFreePlaces() == 0 || !"OUVERT".equals(parking.getInfo().getStatus()));
            parks.removeIf(availableParksPredicate);
            return parks;
        }
        return parks;
    }

    /**
     * Converts decimal degrees to radians
     *
     * @param deg the degree variable to convert
     * @return {@code Double} the radian value
     * @throws IllegalArgumentException if deg is null
     */
    public static Double degToRad(Double deg) throws IllegalArgumentException {
        Double result;
        if (deg != null) {
            result = (deg * Math.PI / 180.0);
        } else {
            throw new IllegalArgumentException("Null parameter");
        }
        return result;
    }

    /**
     * Converts radian to decimal degree
     *
     * @param rad the degree variable to convert
     * @return {@code Double} the degree decimal value
     * @throws IllegalArgumentException if rad is null
     */
    public static Double radToDeg(Double rad) throws IllegalArgumentException {
        Double result;
        if (rad != null) {
            result = (rad * 180 / Math.PI);
        } else {
            throw new IllegalArgumentException("Null parameter");
        }
        return result;
    }

    /**
     * Round decimal number to 2 decimal places
     *
     * @param dec the decimal number to round
     * @return {@code Double} rounded number with 2 decimal places
     * @throws IllegalArgumentException if dec is null
     */
    public static Double roundOffTo2DecPlaces(Double dec) throws IllegalArgumentException {
        Double result;
        if (dec != null) {
            result = Math.round(dec * 100.0) / 100.0;
        } else {
            throw new IllegalArgumentException("Null parameter");
        }
        return result;
    }
}
