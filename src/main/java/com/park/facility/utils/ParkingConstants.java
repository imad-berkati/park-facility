package com.park.facility.utils;

public  abstract class ParkingConstants {

    public static final String CAR_PARKS_URL_GET = "https://data.rennesmetropole.fr/api/records/1.0/search/" +
            "?dataset=export-api-parking-citedia";

    public static final String CAR_PARKS_BY_ZONE_URL_GET = CAR_PARKS_URL_GET+"&geofilter.distance={geoDistanceParam}";
}
