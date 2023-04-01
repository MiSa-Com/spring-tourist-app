package com.ms.tourist_app.application.utils;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.*;
<<<<<<< HEAD
<<<<<<< HEAD
import com.ms.tourist_app.application.constants.AppConst;
=======
>>>>>>> 59eea36 (search theo address: gan Done)
=======
import com.ms.tourist_app.application.constants.AppConst;
>>>>>>> 291b8a6 (Modify in GoogleMapApi to review)

public class GoogleMapApi {
    private static final String KEY_MAP_API = "YOUR_KEY_HERE";
    private static final GeoApiContext MY_API_CONTEXT = new GeoApiContext.Builder().apiKey(KEY_MAP_API).build();

    public static LatLng getLatLng(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(MY_API_CONTEXT, address).await();

            return results[0].geometry.location;
        } catch (Exception ex) {
            return null;
        }
    }

    public static double toRad(double angleInDegree) {
<<<<<<< HEAD
<<<<<<< HEAD
        return angleInDegree * Math.PI / AppConst.unitRad;
    }

    /**
     * @param origin
     * @param destination
     * @return Distance in km
     */
    public static double getFlightDistanceInKm(LatLng origin, LatLng destination) {
        double dLat = toRad(destination.lat - origin.lat); // deg2rad below From: http://www.movable-type.co.uk/scripts/latlong.html
        double dLon = toRad(destination.lng - origin.lng);
        double latOrigin = toRad(origin.lat);
        double latDest = toRad(destination.lat);

        double alpha = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
                + Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0) * Math.cos(latOrigin) * Math.cos(latDest);
        double angleInRad = 2 * Math.atan2(Math.sqrt(alpha), Math.sqrt(1.0 - alpha));
        double distance = AppConst.radiusEarth * angleInRad;

        return Math.round(distance * 1000.0) / 1000.0;
=======
        return angleInDegree * Math.PI / 180.0;
=======
        return angleInDegree * Math.PI / AppConst.unitRad;
>>>>>>> 291b8a6 (Modify in GoogleMapApi to review)
    }

    /**
     * @param origin
     * @param destination
     * @return Distance in km
     */
    public static double getFlightDistanceInKm(LatLng origin, LatLng destination) {
        double dLat = toRad(destination.lat - origin.lat); // deg2rad below From: http://www.movable-type.co.uk/scripts/latlong.html
        double dLon = toRad(destination.lng - origin.lng);
        double latOrigin = toRad(origin.lat);
        double latDest = toRad(destination.lat);

        double alpha = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
                + Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0) * Math.cos(latOrigin) * Math.cos(latDest);
        double angleInRad = 2 * Math.atan2(Math.sqrt(alpha), Math.sqrt(1.0 - alpha));
        double distance = AppConst.radiusEarth * angleInRad;

<<<<<<< HEAD
        return Math.round(d * 1000.0) / 1000.0;
>>>>>>> 59eea36 (search theo address: gan Done)
=======
        return Math.round(distance * 1000.0) / 1000.0;
>>>>>>> 291b8a6 (Modify in GoogleMapApi to review)
    }

    public static Double getTripDurationByBicycleInMinute(LatLng origin, LatLng destination, LatLng... steps) {
        return getTripDurationOrDistance(TravelMode.BICYCLING, true, origin, destination, steps);
    }

    public static Double getTripDistanceByCarInKm(LatLng origin, LatLng destination, LatLng... steps) {
        return getTripDurationOrDistance(TravelMode.DRIVING, false, origin, destination, steps);
    }

    public static Double getTripDurationOrDistance(TravelMode mode, boolean duration, LatLng origin, LatLng destination, LatLng... steps) {

        DirectionsApiRequest request = DirectionsApi.getDirections(MY_API_CONTEXT, origin.toString(), destination.toString());
        request.mode(mode);
        request.region("fr");

        if (steps.length > 0) {

            String[] stringSteps = new String[steps.length];
            for (int i = 0; i < steps.length; i++) {
                stringSteps[i] = steps[i].toString();
            }

            request.waypoints(stringSteps);
        }

        double cumulDistance = 0.0;
        double cumulDuration = 0.0;

        try {
            DirectionsResult result = request.await();
            DirectionsRoute[] directions = result.routes;

            for (int legIndex = 0; legIndex < directions[0].legs.length; legIndex++) {

                cumulDistance += directions[0].legs[legIndex].distance.inMeters / 1000.0;
                cumulDuration += Math.ceil(directions[0].legs[legIndex].duration.inSeconds / 60.0);
            }

        } catch (Exception ex) {
            return null;
        }

        if (duration) {
            return cumulDuration;
        } else {
            return cumulDistance;
        }
    }

    public static boolean hasInvalidApiKey() {
        return KEY_MAP_API.startsWith("YOUR_KEY_HERE");
    }
}
