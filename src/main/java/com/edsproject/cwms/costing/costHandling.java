/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.costing;

import java.lang.reflect.Array;

import com.edsproject.cwms.fileHandling.fileIO;
import com.edsproject.cwms.miscellaneous.doubleChecker;

public class costHandling {

    boolean _debug = true; //Next release to change it to read IO config for ease setting

    fileIO fileIO = new fileIO();
    doubleChecker doubleChecker = new doubleChecker();
    private String selection;
    private double basePrice;
    private String specialPrice_preDetermined;
    private double nettPrice;

    public String costCalculation(String input) {
        String[] array_data = new String[0];
        if (!input.startsWith(",") && !input.startsWith("null")) {
            if (_debug) System.out.println("costing/costHandling/costCalculation");
            array_data = input.split(",");
            selection = (String) array_data[3];
            basePrice = Double.parseDouble(fileIO.io_priceList("getPriceList", selection));
            specialPrice_preDetermined = (String) array_data[5];
            //the predetermined special price must check if it comes with + - or something else, need to handle empty string, it means stick default as-is
            if (specialPrice_preDetermined.startsWith("-")) {
                nettPrice = basePrice - Double.parseDouble(specialPrice_preDetermined.substring(1));
            } else if (specialPrice_preDetermined.startsWith("+")) {
                nettPrice = basePrice + Double.parseDouble(specialPrice_preDetermined.substring(1));
            } else if (doubleChecker.isDouble(specialPrice_preDetermined)) {
                nettPrice = basePrice + Double.parseDouble(specialPrice_preDetermined);
            } else if (specialPrice_preDetermined.isBlank()) {
                nettPrice = basePrice;
            } else {
                array_data[5] = "Special Price contain incorrect format";
                return String.join(",", array_data);
            }
            array_data[5] = String.valueOf(nettPrice);
        }
        if (_debug) System.out.println("costing/costHandling/costCalculation//nettPrice=" + nettPrice);
        if (_debug) System.out.println("costing/costHandling/costCalculation//return=" + String.join(",", array_data));
        return String.join(",", array_data);
    }

}
