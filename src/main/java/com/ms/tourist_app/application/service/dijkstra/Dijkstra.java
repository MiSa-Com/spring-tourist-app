package com.ms.tourist_app.application.service.dijkstra;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.domain.entity.Address;

import java.util.List;

public class Dijkstra {
    private Dijkstra() {}
    public static Double[][] calculateTimeInMinutes(List<Address> listAddress, TravelMode mode, boolean isDuration) {
        Double[][] timeInMinutes = new Double[listAddress.size()][listAddress.size()];
        for (int i = 0 ; i < listAddress.size() ; i++) {
            Address origin = listAddress.get(i);
            double originLat = origin.getLatitude();
            double originLng = origin.getLongitude();
            LatLng originCoordinate = new LatLng(originLat, originLng);
            for (int j = 0 ; j < listAddress.size() ; j++) {
                Address destination = listAddress.get(j);
                double destLat = destination.getLatitude();
                double destLng = destination.getLongitude();
                LatLng destCoordinate = new LatLng(destLat, destLng);
                if (i != j) {
                    timeInMinutes[i][j] = GoogleMapApi.getTripDurationOrDistance(mode, isDuration, originCoordinate, destCoordinate);
                }
                else {
                    timeInMinutes[i][j] = 0.0;
                }
            }
        }
        return timeInMinutes;
    }
}
