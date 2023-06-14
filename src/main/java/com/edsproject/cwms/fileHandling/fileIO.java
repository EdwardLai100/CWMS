/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.fileHandling;

import com.edsproject.cwms.costing.costHandling;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

//Prefer to use Java NIO for better performance with non-blocking IO.

public class fileIO {

    boolean _debug = true; //Next release to change it to read IO config for ease setting

    public void putItems(String mode, String data) {

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
                if (_debug) System.out.println("fileHandling/fileIO/output_items=File created successfully.");
            }
        } catch (IOException e) {
        }

        switch (mode) {
            case "NewQueue":
                //Directly calculate Costing during queue start
                if (_debug) System.out.println("fileHandling/fileIO/output_items//data input=" + data);
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
                break;
            case "UpdateQueueByNumberPlate":
                try {
                    String numberPlate = null; // Retrieve the numberPlate value you want to update
                    String filePath = file.toString();
                    Path path = Paths.get(filePath);
                    List<String> lines = Files.readAllLines(path);
                    String[] array_data = data.split(",");

                    //Retrieve numberPlate from the incoming data
                    if (array_data.length > 1) {
                        numberPlate = array_data[1];
                    }

                    //Find the numberPlate inside the items file, replace if found
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);
                        String[] columns = line.split(",");
                        if (columns.length > 1 && columns[1].equals(numberPlate)) {
                            lines.set(i, data);
                            break;
                        }
                    }
                    // Write the updated lines back to the file
                    Files.write(path, lines);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
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

        try {
            boolean created = file.createNewFile();
            if (created) {
                if (_debug) System.out.println("fileHandling/fileIO/io_priceList=File created successfully.");
                //Price initialization (since the price list is just newly created, providing default price list)
                String data = "Normal,20\nAdvance,50\nWash Wax and Polish,108\nCeramic Coating,258\nCustomized,20";
                Files.writeString(path, data, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
        }

        switch (type) {
            case "setPriceList": //Future release will allow customer to set their own price list through UI
            case "getPriceList":
                try {
                    List<String> lines = Files.readAllLines(path);
                    for (String line : lines) {
                        String[] columns = line.split(",");
                        if (columns[0].equals(arg1)) {
                            priceList = (String) Array.get(columns, 1);
                            if (_debug) System.out.println("fileHandling/fileIO/io_priceList//priceList=" + priceList);
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

    public String getFlowList(String type, String currentFlow) {

        //naming conversion : flow = state;
        final File file = new File("C:/CWMS/_flowList/flows.csv");
        final Path path = Path.of(file.toString());
        final String directory = "C:/CWMS/_flowList/";
        String updatedFlow = currentFlow;
        final File dir = new File(directory);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            boolean created = file.createNewFile();
            if (created) {
                if (_debug) System.out.println("fileHandling/fileIO/getFlowList=File created successfully.");
                //State initialization (since the flow list is just newly created, providing default flow list)
                String data = "Queued,Washing,Drying,Interior,Detailing,Completed";
                Files.writeString(path, data, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
        }

        if (_debug) System.out.println("fileHandling/fileIO/getFlowList//type|currentFlow=" + type + "|" + currentFlow);

        switch (type) {
            case "getPreviousFlow":
                try {
                    List<String> lines = Files.readAllLines(path);
                    for (String line : lines) {
                        String[] columns = line.split(",");
                        for (int i = 1; i < columns.length; i++) {
                            if (columns[i].equals(currentFlow)) {
                                if (columns[i - 1].equals(currentFlow)) {
                                    updatedFlow = columns[i];
                                } else {
                                    updatedFlow = columns[i - 1];
                                }
                                if (_debug) System.out.println("fileHandling/fileIO/getFlowList//state=" + updatedFlow);
                                return updatedFlow;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "getNextFlow":
                try {
                    List<String> lines = Files.readAllLines(path);
                    for (String line : lines) {
                        String[] columns = line.split(",");
                        for (int i = 0; i < columns.length - 1; i++) {
                            if (columns[i].equals(currentFlow)) {
                                if (i + 1 == columns.length - 1) {
                                    updatedFlow = columns[columns.length - 1];
                                } else {
                                    updatedFlow = columns[i + 1];
                                }
                                if (_debug) System.out.println("fileHandling/fileIO/getFlowList//state=" + updatedFlow);
                                return updatedFlow;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return updatedFlow;
    }

    public String getItems(String numberPlate) {
        final File file = new File("C:/CWMS/_storage/items.csv");
        final Path path = Path.of(file.toString());
        String items = "";

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] columns = line.split(",");
                if (columns.length > 1 && columns[1].equals(numberPlate)) {
                    items = line;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (_debug) System.out.println("fileHandling/fileIO/getItems//items=" + items);
        return items;
    }

    public boolean getGeneratedPDF(String numberPlate, HttpServletResponse response) {
        String filePath = "C:/CWMS/_output/" + numberPlate + ".pdf";
        File file = new File(filePath);
        if (file.exists()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + numberPlate + ".pdf");
            try (InputStream inputStream = new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
            }
            return true;
        } else {
            return false;
        }
    }
}
