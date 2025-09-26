package com.project.util;

public class StringCaseUtil {

 public static String snakeToCamel(String str) {
     if (str == null || str.isEmpty()) {
         return str;
     }
     return java.util.regex.Pattern.compile("_([a-z])")
             .matcher(str)
             .replaceAll(m -> m.group(1).toUpperCase());
 }
}