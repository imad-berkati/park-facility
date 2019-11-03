package com.park.facility.util;

/**
 * This abstract class contains shared static variables
 *
 * @author Imad Berkati
 */
public abstract class ParkingConstants {

    public static final String CAR_PARKS_URL_GET = "https://data.rennesmetropole.fr/api/records/1.0/search/" +
            "?dataset=export-api-parking-citedia";
    public static final String CAR_PARKS_BY_ZONE_URL_GET = CAR_PARKS_URL_GET + "&geofilter.distance={geoDistanceParam}";
    public static final Double RENNES_TRAIN_STATION_LATITUDE = 48.1034827;
    public static final Double RENNES_TRAIN_STATION_LONGITUDE = -1.6722622;
    public static final Double KLEBER_PARK_LATITUDE = 48.110764;
    public static final Double KLEBER_PARK_LONGITUDE = -1.6731652;
    // Distance from GoogleMaps
    public static final Double DISTANCE_BETWEEN_KLEBER_AND_TRAIN_STATION = 812.46;

}
