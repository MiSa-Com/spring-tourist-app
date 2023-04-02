package com.ms.tourist_app.application.constants;

public class AppEnv {
    public static final class ApiGoogle{
        public ApiGoogle() {
        }
    }
    public static final class Cloudinary{
        public Cloudinary() {
        }
        public static final String cloudName = "none01";
        public static final String apiKey = "975898585293148";
        public static final String apiSecret = "JJqFPcPDDF08WM4hL-K4ysUbXzo";
    }
    public static final class Swagger{
        public Swagger() {
        }
        public static final String pathRegex = "/.*";
        public static final String basePackage = "com.ms.tourist_app.adapter.web.v1.controller";
        public static final String title = "Ngo Ngoc Sang";
        public static final String description = "TouristApp";
        public static final String nameContact = "Ngo Ngoc Sang";
        public static final String urlContact = "...";
        public static final String emailContact = "ngocsangair01@gmail.com";
        public static final String license = "Apache 2.0";
        public static final String licenseUrl = "";
        public static final String version = "1.0.0";
    }
    public static final class MessageSourceConfig{
        public static final String baseName = "classpath:i18n/messages";
        public static final String utf8 = "UTF-8";
    }
    public static final class JwtConfig{
        public static final String secretKey = "1231231";
        public static final Integer timeExpiration = 864000;
    }
}
