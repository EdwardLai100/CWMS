/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * CHANGE HISTORY
 * DATE              AUTHOR                    DESCRIPTION
 * 8 JUNE 2023       EDWARD LAI                INITIAL RELEASE
 * 11 JUNE 2023      EDWARD LAI                ADD COSTING FEATURE, CODE RESTRUCTURE, ENHANCE FILE IO
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.fileHandling;

import com.edsproject.cwms.costing.costHandling;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

//Prefer to use Java NIO for better performance with non-blocking IO.

public class fileIO {

    boolean _debug = true; //Next release to change it to read IO config for ease setting

    public void output_items(String data) {

        final costHandling costHandling = new costHandling();
        final File file = new File("C:/CWMS/_storage/items.csv");
        final String directory = "C:/CWMS/_storage/";
        final File dir = new File(directory);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            boolean created = file.createNewFile();
            if (created) {
                if(_debug) System.out.println("File created successfully.");
            }
        } catch (IOException e) {
        }

        //Directly calculate Costing during inserting queue
        if(_debug) System.out.println("data input=" + data);
        String result = costHandling.costCalculation(data);

        try {
            String filePath = file.toString();
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            if ((!lines.contains(result)) && (!data.startsWith(",")) && (!result.startsWith("null"))) {
                lines.add(result);
                String content = String.join(System.lineSeparator(), lines) + System.lineSeparator();
                Files.write(path, content.getBytes());
            }
        } catch (Exception e) {
        }

    }

    public String io_priceList(String type, String arg1) {

        final File file = new File("C:/CWMS/_priceList/priceList.csv");
        final Path path = Path.of(file.toString());
        final String directory = "C:/CWMS/_priceList/";
        String priceList = null;
        final File dir = new File(directory);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        switch (type) {
            case "setPriceList":
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        if(_debug) System.out.println("File created successfully.");
                        //Price initialization (since the price list is just newly created, providing default price list)
                        String data = "Normal,20\nAdvance,50\nWash Wax and Polish,108\nCeramic Coating,258\nCustomized,20";
                        Files.writeString(path, data, StandardOpenOption.CREATE);
                    }
                } catch (IOException e) {
                }
                break;
            case "getPriceList":
                try {
                    List<String> lines = Files.readAllLines(path);
                    for (String line : lines) {
                        String[] columns = line.split(",");
                        if (columns[0].equals(arg1)) {
                            priceList = (String) Array.get(columns, 1);
                            if(_debug) System.out.println("priceList=" + priceList);
                            break;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return priceList;
    }

}
