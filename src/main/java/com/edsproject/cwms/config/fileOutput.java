package com.edsproject.cwms.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                       CHANGE HISTORY
 * DATE              AUTHOR                    DESCRIPTION
 * 8 JUNE 2023       EDWARD LAI                INITIAL RELEASE
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import java.nio.file.Paths;
import java.util.List;

public class fileOutput {

    public void output(String data) {

        File file = new File("C:/CWMS/_storage/items.txt");
        final String directory = "C:/CWMS/_storage/";
        // Create the directory if it doesn't exist
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            boolean created = file.createNewFile();
            if (created) {
                System.out.println("File created successfully.");
            }
        } catch (IOException e) {
        }

        try {
            String filePath = file.toString();
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            System.out.println("data input=" + data);
            if ((!lines.contains(data)) && (!data.startsWith(",")) && (!data.startsWith("null"))) {
                lines.add(data);
                String content = String.join(System.lineSeparator(), lines) + System.lineSeparator();
                Files.write(path, content.getBytes());
            }
        } catch (Exception e) {
        }
        //No need return anything yet as not used.
        //return "Data saved!";
    }

}
