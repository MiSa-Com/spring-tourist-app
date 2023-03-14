package com.ms.tourist_app;

import com.ms.tourist_app.application.utils.GeoNetApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TouristAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristAppApplication.class, args);
        String add = "Golden palace, Phố Đồng Me, Mễ Trì, Nam Từ Liêm, Hanoi, Vietnam";
        System.out.println(GeoNetApi.getLatLng(add));
    }

}
