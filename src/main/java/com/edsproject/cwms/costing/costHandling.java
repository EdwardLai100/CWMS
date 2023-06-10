/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * CHANGE HISTORY
 * DATE              AUTHOR                    DESCRIPTION
 * 11 JUNE 2023      EDWARD LAI                ADD COSTING FEATURE, CODE RESTRUCTURE, ENHANCE FILE IO
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.costing;

import java.lang.reflect.Array;

import com.edsproject.cwms.fileHandling.fileIO;

public class costHandling {

    boolean _debug = true; //Next release to change it to read IO config for ease setting

    fileIO fileIO = new fileIO();
    private String selection;
    private double basePrice;
    private String specialPrice_preDetermined;
    private double specialPrice_postDetermined;
    private double nettPrice;

    public String costCalculation(String input) {
        String[] array_data = new String[0];
        if (!input.startsWith(",") && !input.startsWith("null")) {
            if (_debug) System.out.println("costCalculation");
            array_data = input.split(",");
            String selection = (String) array_data[3];
            double basePrice = Double.parseDouble(fileIO.io_priceList("getPriceList", selection));
            String specialPrice_preDetermined = (String) array_data[5];
            double specialPrice_postDetermined;
            //the pre determined special price must check if it comes with + - or something else, need to handle empty string, it means stick default as-is
            if (specialPrice_preDetermined.startsWith("+")) {
                specialPrice_postDetermined = Double.parseDouble(specialPrice_preDetermined.substring(1));
            } else {
                specialPrice_postDetermined = 0 - Double.parseDouble(specialPrice_preDetermined.substring(1));
            }
            double nettPrice = basePrice + specialPrice_postDetermined;
            array_data[5] = String.valueOf(nettPrice);
        }
        return String.join(",", array_data);
    }

}
