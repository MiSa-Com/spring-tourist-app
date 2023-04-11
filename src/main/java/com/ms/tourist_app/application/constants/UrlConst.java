package com.ms.tourist_app.application.constants;

public class UrlConst {

    private UrlConst() {
    }
    public static final String apiV1 = "/api/v1";
    public static final String apiV1Auth = "/api/v1/auth";
    public static final String api = "/api";
    public static final String idLink = "/{id}";
    public static final String id = "id";
    public static final class User {
        private User() {
        }
        public static final String users = "/users";
        public static final String getUserById = users+idLink;
    }

    public static final class Auth{
        public Auth() {
        }
        public static final String login = "/login";
        public static final String signUp = "/signup";
    }

    public static final class Address{
        public static final String address = "/addresses";
        public static final String getAddressId = address+idLink;
    }
    public static final class Itinerary {
        public static final String itinerary = "/itineraries";
        public static final String getByIdUser = itinerary + idLink;
        public static final String bestItinerary = "/best-itinerary";
        public static final String recommendItinerary = "/recommend-itinerary";
    }
    public static final class DestinationType{
        public static final String destinationType = "/destination-types";
        public static final String getDestinationTypeId = destinationType+idLink;
    }
    public static final class Destination{
        public static final String destination = "destinations";
        public static final String getDestinationId = destination+idLink;
    }

    public static final class Hotel{
        public static final String hotel = "hotels";
        public static final String getHotelById = hotel+idLink;
    }
    public static final class Province{
        public static final String province = "provinces";
        public static final String getProvinceById = province+idLink;
    }
}
