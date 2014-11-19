package com.appframework.util;

/**
 * Created by Eric on 14/11/7.
 */
public class DegreesUtil {

    public static String  parseLonLat(double f){
        int deg = (int)f;
        int min = (int)((f-deg)*60);
        int sed = (int)(((f - deg)*60 - min ) * 60);
        return deg+","+min+","+sed;
    }

    public static void main(String[] args) {
        double latitude = 31.206092;

        System.out.println(DegreesUtil.parseLonLat(latitude));
    }
}
