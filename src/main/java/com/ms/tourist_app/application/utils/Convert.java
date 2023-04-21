package com.ms.tourist_app.application.utils;

import org.springframework.stereotype.Service;

public class Convert {
    public static String withSpace(String input){
        return input.replace("-"," ");
    }

    public static String withoutSpace(String input){
        return input.replace("-","");
    }
}
