package com.park.facility.utils;

import com.park.facility.utils.enums.DistanceUnit;

//TODO Add JavaDoc
public abstract class ParkingUtils {

    public static double getDistanceBetweenTwoPoints(Double latitudePoint1, Double longitudePoint1, Double latitudePoint2,
                                                     Double longitudePoint2, DistanceUnit unit) throws IllegalArgumentException {
        double theta, distance;
        if (latitudePoint1 == null || longitudePoint1 == null || latitudePoint2 == null || longitudePoint2 == null
                || unit == null) {
            throw new IllegalArgumentException("getDistanceBetweenTwoPoints : Parameters error");
        }
        theta = longitudePoint1 - longitudePoint2;
        distance = (Math.sin(deg2rad(latitudePoint1)) * Math.sin(deg2rad(latitudePoint2))) +
                (Math.cos(deg2rad(latitudePoint1)) * Math.cos(deg2rad(latitudePoint2)) * Math.cos(deg2rad(theta)));
        distance = rad2deg(Math.acos(distance));
        switch (unit) {
            case KILOMETER:
                distance = roundOffTo2DecPlaces(distance * 60 * 1.1515 * 1.609344);
                break;
            case METER:
                distance = roundOffTo2DecPlaces(distance * 60 * 1.1515 * 1.609344 * 1000).intValue();
                break;
            default:
                throw new IllegalArgumentException("getDistanceBetweenTwoPoints : don't support the unit "+unit);
        }

        return distance;
    }

    public static Double deg2rad(Double deg) throws IllegalArgumentException {
        Double result;
        if (deg != null)
            result = (deg * Math.PI / 180.0);
        else
            throw new IllegalArgumentException("the parameter 'deg' must not be null");
        return result;
    }

    public static Double rad2deg(Double rad) throws IllegalArgumentException {
        Double result;
        if (rad != null)
            result = (rad * 180 / Math.PI);
        else
            throw new IllegalArgumentException("the parameter 'rad' must not be null");
        return result;
    }

    public static Double roundOffTo2DecPlaces(Double dec) throws IllegalArgumentException {
        Double result;
        if (dec != null)
            result = Math.round(dec * 100.0) / 100.0;
        else
            throw new IllegalArgumentException("the parameter 'dec' must not be null");
        return result;
    }
}
