package com.example.Utilities;

public class StringUtil {

  public static String cleanString(String str) {
    return str.replaceAll("\"", "");
  }

  public static boolean stringIsNotNullOrEmpty(String str) {
    return (str != null && !str.trim().isEmpty());
  }
}
