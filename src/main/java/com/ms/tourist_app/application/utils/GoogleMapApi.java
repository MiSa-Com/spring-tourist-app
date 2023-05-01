package com.ms.tourist_app.application.utils;

import com.github.slugify.Slugify;
import com.google.maps.*;
import com.google.maps.model.*;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppEnv;
import com.ms.tourist_app.domain.entity.Address;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapApi {
    private static final String KEY_MAP_API = AppEnv.keyGoogleMap;
    private static final GeoApiContext MY_API_CONTEXT = new GeoApiContext.Builder().apiKey(KEY_MAP_API).build();

    public static LatLng getLatLng(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(MY_API_CONTEXT, address).await();

            return results[AppConst.MapApi.defaultIndex].geometry.location;
        } catch (Exception ex) {
            throw new NullPointerException("Cannot get LatLng from address: " + address);
        }
    }

    public static String getFormattedAddressFromLngLat(LatLng latLng) {
        try {
            GeocodingApiRequest request = GeocodingApi.reverseGeocode(MY_API_CONTEXT, latLng);
            request.language(AppConst.MapApi.language);
            GeocodingResult[] results = request.await();
            return results[AppConst.MapApi.defaultIndex].formattedAddress;
        } catch (Exception ex) {
            throw new NullPointerException("Cannot get province from LatLng: " + latLng);
        }
    }

    public static double toRad(double angleInDegree) {
        return angleInDegree * Math.PI / AppConst.MapApi.unitRad;
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
        double distance = AppConst.MapApi.radiusEarth * angleInRad;

        return Math.round(distance * 1000.0) / 1000.0;
    }

    public static Double getTripDurationByBicycleInMinute(LatLng origin, LatLng destination, LatLng... steps) {
        return getTripDurationOrDistance(TravelMode.BICYCLING, origin, destination, steps)[1];
    }

    public static Double getTripDistanceByCarInKm(LatLng origin, LatLng destination, LatLng... steps) {
        return getTripDurationOrDistance(TravelMode.DRIVING, origin, destination, steps)[0];
    }

    public static double[] getTripDurationOrDistance(TravelMode mode, LatLng origin, LatLng destination, LatLng... steps) {

        DirectionsApiRequest request = DirectionsApi.getDirections(MY_API_CONTEXT, origin.toString(), destination.toString());
        request.mode(mode);
        request.region("vn");

        if (steps.length > 0) {

            String[] stringSteps = new String[steps.length];
            for (int i = AppConst.MapApi.defaultIndex; i < steps.length; i++) {
                stringSteps[i] = steps[i].toString();
            }

            request.waypoints(stringSteps);
        }

        double cumulDistance = 0.0;
        double cumulDuration = 0.0;

        try {
            DirectionsResult result = request.await();
            DirectionsRoute[] directions = result.routes;

            for (int legIndex = AppConst.MapApi.defaultIndex; legIndex < directions[AppConst.MapApi.defaultIndex].legs.length; legIndex++) {

                cumulDistance += directions[AppConst.MapApi.defaultIndex].legs[legIndex].distance.inMeters / 1000.0;
                cumulDuration += Math.ceil(directions[AppConst.MapApi.defaultIndex].legs[legIndex].duration.inSeconds / 60.0);
            }

        } catch (Exception ex) {
            return null;
        }

        double[] cumulArray = new double[2];
        cumulArray[0] = cumulDistance;
        cumulArray[1] = cumulDuration;
        return cumulArray;
    }

    public static List<Address> findAddressFromText(String address, int nbResult) {
        Slugify slugify = new Slugify();
        try {
            TextSearchRequest searchRequest = new TextSearchRequest(MY_API_CONTEXT);
            PlacesSearchResponse searchResponse = searchRequest.query(address).await();
            List<Address> resultAdds = new ArrayList<>();
            for (int i = AppConst.MapApi.defaultIndex; i < nbResult && i < searchResponse.results.length; i++) {
                Address addressToAdd = new Address();
                addressToAdd.setLatitude(searchResponse.results[i].geometry.location.lat);
                addressToAdd.setLongitude(searchResponse.results[i].geometry.location.lng);
                addressToAdd.setDetailAddress(searchResponse.results[i].formattedAddress);
                slugify.withTransliterator(true);
                addressToAdd.setSlug(slugify.slugify(searchResponse.results[i].formattedAddress));
                resultAdds.add(addressToAdd);
            }
            return resultAdds;
        } catch (Exception ex) {
            throw new NullPointerException("Cannot find address");
        }
    }

    public static boolean hasInvalidApiKey() {
        return KEY_MAP_API.startsWith(AppEnv.keyGoogleMap);
    }
}
