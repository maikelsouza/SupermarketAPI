package com.qikserve.supermarket.util;

import java.text.DecimalFormat;

public class CalculationUtil {

    public static double roundToTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedValue = df.format(value);
        formattedValue = formattedValue.replace(",", ".");
        return Double.parseDouble(formattedValue);
    }
}
