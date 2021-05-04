package com.example.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthUtil {

  private static final String publicKey = "37e6736486c94fddb5e467bed7d7569a";
  private static final String privateKey = "5d36572150a08a79c76a71167eb220221b1796d3";

  private static final String md5 = "MD5";

  public static String getAuthParameters() {
    Long tsLong = System.currentTimeMillis() / 1000;
    final String ts = tsLong.toString();
    String hash = getMd5(ts + privateKey + publicKey);
    return String.format("ts=%s&apikey=%s&hash=%s", ts, publicKey, hash);
  }

  private static String getMd5(String input) {
    try {
      // Create MD5 Hash
      MessageDigest digest = MessageDigest.getInstance(md5);
      digest.update(input.getBytes());
      byte messageDigest[] = digest.digest();
      // Create Hex String
      StringBuilder hexString = new StringBuilder();
      for (byte aMessageDigest : messageDigest) {
        String h = Integer.toHexString(0xFF & aMessageDigest);
        while (h.length() < 2) {
          h = "0" + h;
        }
        hexString.append(h);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }
}
