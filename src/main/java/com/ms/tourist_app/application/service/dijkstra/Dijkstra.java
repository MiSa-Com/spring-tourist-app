package com.ms.tourist_app.application.service.dijkstra;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.domain.entity.Destination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dijkstra {
    private Dijkstra() {}
    public static Double[][] calculateTimeInMinutes(List<Destination> listDestination, TravelMode mode, boolean isDuration) {
        Double[][] timeInMinutes = new Double[listDestination.size()][listDestination.size()];
        for (int i = 0 ; i < listDestination.size() ; i++) {
            Destination origin = listDestination.get(i);
            double originLat = origin.getAddress().getLatitude();
            double originLng = origin.getAddress().getLongitude();
            LatLng originCoordinate = new LatLng(originLat, originLng);
            for (int j = 0 ; j < listDestination.size() ; j++) {
                Destination destination = listDestination.get(j);
                double destLat = destination.getAddress().getLatitude();
                double destLng = destination.getAddress().getLongitude();
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
