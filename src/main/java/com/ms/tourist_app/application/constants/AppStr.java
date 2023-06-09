package com.ms.tourist_app.application.constants;

public class AppStr {
    private AppStr() {

    }


    public static final class Exception {
        public static final String duplicate = "Duplicate";
        public static final String notFound = "Not found";
        public static final String forbidden = "You are not logged in";

        public Exception() {
        }
    }

    public static final class Base {
        public static final String whiteSpace = " ";
        public static final String dash = "-";
        public static final String id = "id";
        public static final String createBy = "createBy";
        public static final String createAt = "createAt";
        public static final String updateBy = "updateBy";
        public static final String updateAt = "updateAt";
        public static final String status = "status";

        public Base() {
        }
    }

    public static final class User {
        public User() {
        }

        public static final String tableUser = "users";
        public static final String user = "User";
        public static final String firstName = "first_name";
        public static final String lastName = "last_name";
        public static final String dateOfBirth = "date_of_birth";
        public static final String address = "address";
        public static final String telephone = "telephone";
        public static final String email = "email";
        public static final String password = "password";
        public static final String favoriteUser = "favoriteUsers";
        public static final String joinTableUser = "user";
        public static final String idAddress = "id_address";
        public static final String idDestination = "id_destination";
        public static final String userFavDestination = "This destination already existed in your list of favorite";
        public static final String notFoundFavoriteDestination = "This destination" + AppStr.Base.whiteSpace + AppStr.Exception.notFound + AppStr.Base.whiteSpace + "in your list of favorite";
    }

    public static final class Weather {
        public static final String tableCurrentWeather = "current_weathers";
        public static final String tableWeather = "weathers";
        public static final String idProvince = "id_province";
        public static final String province = "province";
        public static final String dateTime = "date_time";
        public static final String temp = "temp";
        public static final String feelLike = "feels_like";
        public static final String tempMin = "temp_min";
        public static final String tempMax = "temp_max";
        public static final String humidity = "humidity";
        public static final String main = "main";
        public static final String description = "description";
    }

    public static final class Hotel {
        public Hotel() {
        }

        public static final String tableHotel = "hotel";
        public static final String name = "name";
        public static final String address = "address";
        public static final String description = "description";
        public static final String telephone = "telephone";
        public static final String idUser = "id_user";
        public static final String idAddress = "id_address";
    }

    public static final class ImageHotel {
        public ImageHotel() {
        }

        public static final String tableImageHotel = "image_hotel";
        public static final String idHotel = "id_hotel";
    }

    public static final class Room {
        public Room() {
        }

        public static final String tableRoom = "room";
        public static final String name = "name";
        public static final String price = "price";
        public static final String destination = "destination";
        public static final String calendar = "calendar";
        public static final String status = "status";
        public static final String superficie = "superficie";
        public static final String idHotel = "id_hotel";
        public static final String room = "room";
    }

    public static final class Role {
        public Role() {
        }

        public static final String tableRole = "role";
        public static final String name = "name";
        public static final String joinTableUserRole = "user_role";
        public static final String userRole = "ROLE_USER";
        public static final String adminRole = "ROLE_ADMIN";
    }


    public static final class ImageDestination {
        public ImageDestination() {
        }

        public static final String tableImageDestination = "image_destination";
        public static final String link = "link";
        public static final String idDestination = "id_destination";
    }

    public static final class ImageCommentDestination {
        public ImageCommentDestination() {
        }

        public static final String tableImageComment = "image_comment_destination";
        public static final String link = "link";
        public static final String idUser = "id_user";
        public static final String idDestination = "id_destination";
        public static final String idCommentDestination = "id_comment_destination";
    }

    public static final class ImageCommentHotel {
        public ImageCommentHotel() {
        }

        public static final String tableImageComment = "image_comment_hotel";
        public static final String link = "link";
        public static final String idUser = "id_user";
        public static final String idHotel = "id_hotel";
    }

    public static final class Itinerary {
        public Itinerary() {
        }

        public static final String tableItinerary = "itineraries";
        public static final String itinerary = "itinerary";
        public static final String idUser = "id_user";
        public static final String travelMode = "travel_mode";

    }

    public static final class Address {
        public Address() {
        }

        public static final String tableAddress = "addresses";
        public static final String longitude = "longitude";
        public static final String latitude = "latitude";
        public static final String address = "address";
        public static final String idProvince = "id_province";
        public static final String detailAddress = "detail_address";
        public static final String slug = "slug";
        public static final String slugWithoutSpace = "slug_without_space";
        public static final String slugWithSpace = "slug_with_space";
    }


    public static final class Destination {
        public Destination() {
        }

        public static final String name = "name";
        public static final String address = "address";
        public static final String description = "description";
        public static final String tableDestination = "destination";
        public static final String joinTableDestinationUser = "destination_favorite_users";
        public static final String idAddress = "id_address";
        public static final String slug = "slug";
        public static final String idTypeDestination = "id_type_destination";
        public static final String slugWithSpace = "slug_with_space";
        public static final String slugWithoutSpace = "slug_without_space";
    }

    public static final class Validation {
        public Validation() {
        }

        public static final String mustNotNull = "must not null";
        public static final String incorrectFormat = "format incorrect";
    }

    public static final class Response {
        public Response() {
        }

        public static final String getDataSuccess = "Get data success";
        public static final String getDataError = "Get data error";
        public static final String uploadImageFailed = "Upload image failed";
    }

    public static final class Auth {
        public static final String authorization = "Authorization";
        public static final String bearer = "Bearer";
        public static final String inCorrectLogin = "Incorrect user name or password";
        public static final String emailDuplicate = "Email duplicate";
        public static final String notFoundUser = "Not found user";
    }

    public static final class Booking {
        public static final String idUser = "id_user";
        public static final String idRoom = "id_room";
        public static final String idUserElement = "idUser";
        public static final String idRoomElement = "idElement";
        public static final String checkIn = "check_in";
        public static final String checkOut = "check_out";
        public static final String totalPrice = "total_price";
    }

    public static final class CommentDestination {
        public CommentDestination() {
        }

        public static final String idUser = "id_user";
        public static final String idDestination = "id_destination";
        public static final String idUserElement = "idUser";
        public static final String idDestinationElement = "idDestination";
        public static final String tableCommentDestination = "comment_destination";
        public static final String commentDestination = "commentDestination";
        public static final String content = "content";
        public static final String rating = "rating";
        public static final String idCommentDestination = "id";
    }

    public static final class CommentHotel {
        public CommentHotel() {
        }

        public static final String idUser = "id_user";
        public static final String idHotel = "id_hotel";
        public static final String idUserElement = "idUser";
        public static final String idHotelElement = "idHotel";
        public static final String tableCommentHotel = "comment_hotel";
        public static final String content = "content";
        public static final String rating = "rating";
        public static final String commentHotel = "commentHotel";
    }

    public static final class DestinationType {
        public DestinationType() {
        }

        public static final String tableTypeDestination = "type_destination";
        public static final String name = "name";
        public static final String idTypeDestination = "id_type_destination";
        public static final String destinationType = "destinationType";

    }

    public static final class Province {
        public Province() {
        }

        public static final String tableProvince = "province";
        public static final String name = "name";
        public static final String divisionType = "division_type";
        public static final String longitude = "longitude";
        public static final String latitude = "latitude";
        public static final String slug = "slug";
        public static final String slugWithSpace = "slug_with_space";
        public static final String slugWithoutSpace = "slug_without_space";

    }

    public static final class CloudImage {
        public CloudImage() {
        }

        public static final String secureUrl = "secure_url";
        public static final String cloudName = "cloud_name";
        public static final String apiKey = "api_key";
        public static final String apiSecret = "api_secret";
    }

    public static final class AuthorityConstant {
        public AuthorityConstant() {
        }

        public static final String anonymousUser = "anonymous";
        public static final String claimUUID = "username";
        public static final String claimId = "id";
        public static final String refreshToken = "refresh_token";
    }

    public static final class WeatherMap {
        public WeatherMap() {
        }

        public static final String apiKey = "ec2e3acfd4f07b705ff0a828ec6c228e";
        public static final String apiUrl = "http://api.openweathermap.org/data/2.5";
    }

    public static final class Forbiden{
        public Forbiden() {
        }
        public static final String notSignIn = "You have to sign in";
    }

}
