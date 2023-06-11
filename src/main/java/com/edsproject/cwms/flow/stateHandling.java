/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.flow;

import com.edsproject.cwms.fileHandling.fileIO;

public class stateHandling {

    fileIO fileIO = new fileIO();
    boolean _debug = true; //Next release to change it to read IO config for ease setting
    private String item;
    private String currentFlow;
    private String updatedFlow;

    public void stateChange(String type, String numberPlate) {
        String[] array_data = new String[0];
        if (!numberPlate.isBlank()) {
            if (_debug) System.out.println("flow/stateHandling/stateChange//type|numberPlate=" + type + "|" + numberPlate);
            item = fileIO.getItems(numberPlate);//Get the item with numberPlate at index [1]
            array_data = item.split(",");
            currentFlow = array_data[6]; //Get the current state at index [6]

            if (type.equals("revertFlow")) {
                updatedFlow = fileIO.getFlowList("getPreviousFlow", currentFlow);
            } else if (type.equals("nextFlow")) {
                updatedFlow = fileIO.getFlowList("getNextFlow", currentFlow);
            } else {
                array_data[6] = "Error retrieving state";
                fileIO.putItems("UpdateQueueByNumberPlate", String.join(",", array_data));
                //return String.join(",", array_data);
            }
            array_data[6] = String.valueOf(updatedFlow);
        }
        if (_debug) System.out.println("flow/stateHandling/stateChange//flow=" + updatedFlow);
        if (_debug) System.out.println("flow/stateHandling/stateChange//return=" + String.join(",", array_data));
        fileIO.putItems("UpdateQueueByNumberPlate", String.join(",", array_data));
        //return String.join(",", array_data);
    }
}
