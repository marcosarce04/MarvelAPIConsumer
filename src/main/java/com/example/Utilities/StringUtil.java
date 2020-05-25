package com.example.Utilities;

public class StringUtil {

    public static String cleanString(String str) {

        return str.replaceAll("\"","");
        /*for (int i = 0; i < str.length(); i++) {
            if (str.substring(i).toString() == "\"") {
                str.substring(i).replaceAll() = "";
            }
        }*/
    }

    public static boolean stringIsNotNullOrEmpty(String str) {
        return (str != null && !str.trim().isEmpty());
    }
}
