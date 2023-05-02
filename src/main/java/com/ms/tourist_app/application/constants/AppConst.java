package com.ms.tourist_app.application.constants;

public class AppConst {
    public static final class Auth{
        public Auth() {
        }
        public static final Integer bearerSubstring = 7;
    }

    public static final class MapApi{
        public MapApi() {
        }
        public static final double unitRad = 180.0;
        public static final double radiusEarth = 6371.0; // Average radius of Earth (km)
        public static final int defaultIndex = 0;
        public static final int defaultNbResult = 5;
        public static final String language = "vi";
    }

    public static final class ItineraryController {
        public ItineraryController() {
        }
        public static final double recommendedRadius = 10.0;
    }

    public static final class TSP{
        public TSP() {
        }
        public static final int MAX_COST = 40;

        public static final int TIME_LIMIT = 5000; // 5 seconds
    }
    public static final class Temp{
        public Temp() {
        }
        public static final double kToC = 273.15;
    }

}
