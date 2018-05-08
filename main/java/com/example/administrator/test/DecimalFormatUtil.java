package com.example.administrator.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2018-03-29.
 */

public class DecimalFormatUtil {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.########");
    private static DecimalFormat usdFormat = new DecimalFormat("0.##");

    private static NumberFormat format = NumberFormat.getInstance();        //중간에 , 찍기

    public static String getFormatNumber(double num) {
        return decimalFormat.format(num);
    }

    public static String getFormat(int num) {
        return format.format(num);
    }

    public static String getFormat(double num) {
        return format.format(num);
    }

    public static double getUsdForamt(double num) {
        return Double.parseDouble(usdFormat.format(num));
    }
}
