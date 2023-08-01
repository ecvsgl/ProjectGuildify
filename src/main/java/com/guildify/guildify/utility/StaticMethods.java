package com.guildify.guildify.utility;

import java.util.regex.Pattern;

public class StaticMethods {

    //To check unsuitable names
    public static boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]+$", str);
    }
}
