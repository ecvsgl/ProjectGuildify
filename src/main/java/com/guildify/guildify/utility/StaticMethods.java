package com.guildify.guildify.utility;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;

public class StaticMethods {

    //To check unsuitable names
    public static boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]+$", str);
    }

    public static String getJwtFromRequestHeader(String bearerToken){
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication Failed. Please re-login.");
    }
}
