/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                       CHANGE HISTORY
 * DATE              AUTHOR                    DESCRIPTION
 * 8 JUNE 2023       EDWARD LAI                INITIAL RELEASE
 * 11 JUNE 2023      EDWARD LAI                ADD COSTING FEATURE, CODE RESTRUCTURE, ENHANCE FILE IO
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.edsproject.cwms.fileHandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class fileRemoveList {

    public void remove(String numberPlate) {
        File file = new File("C:/CWMS/_storage/items.csv");
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try {
            String filePath = file.toString();
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            List<String> updatedLines = new ArrayList<>();
            boolean removed = false;

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(numberPlate)) {
                    removed = true;
                } else {
                    updatedLines.add(line);
                }
            }

            if (removed) {
                Files.write(path, updatedLines);
                System.out.println("Line removed successfully.");
            } else {
                System.out.println("No matching line found.");
            }
        } catch (IOException e) {
        }
    }
}
