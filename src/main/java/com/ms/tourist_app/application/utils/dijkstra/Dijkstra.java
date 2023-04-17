package com.ms.tourist_app.application.utils.dijkstra;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.domain.entity.Address;

import java.util.List;

public class Dijkstra {
    private Dijkstra() {}
    public static double[][][] calculateTimeInMinutes(List<Address> listAddress, TravelMode mode) {
        double[][][] timeInMinutes = new double[listAddress.size()][listAddress.size()][2];
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
                    timeInMinutes[i][j] = GoogleMapApi.getTripDurationOrDistance(mode, originCoordinate, destCoordinate);
                }
                else {
                    timeInMinutes[i][j][0] = 0.0;
                    timeInMinutes[i][j][1] = 0.0;
                }
            }
        }
        return timeInMinutes;
    }
}
