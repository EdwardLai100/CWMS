/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.service.costing;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class generateCostPDF {
    public void invoice(String csvData) {

        boolean _debug = true; //Next release to change it to read IO config for ease setting
        PDFont pdfFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        String outputPath = "C:/CWMS/_output/test.pdf";
        final String directory = "C:/CWMS/_output/";
        final File dir = new File(directory);
        float margin = 50f; // Margin value in points
        //csvData = "Test Cars 1,VA 10,Sedan / Hatchback,Normal,TEST,20.0,Washing"; //FOR TESTING ONLY

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            // Parse the CSV data
            String[] columns = csvData.split(",");
            String customerName = columns[0]; //carModel
            String invoiceNumber = columns[1]; //numberPlate
            String segment = columns[2]; //segment
            String typeOfWash = columns[3]; //typeOfWash
            String notes = columns[4]; //notes
            double totalPrice = Double.parseDouble(columns[5]); //netPrice
            String Description = segment + " - " + typeOfWash + " - " + notes;

            // Create a new PDF document
            PDDocument document = new PDDocument();

            // Create a new page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a new content stream for drawing on the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size
            contentStream.setFont(pdfFont, 12);

            // Set starting position for drawing
            float startX = 50;
            float startY = page.getMediaBox().getHeight() - 50;

            // Invoice details
            String invoiceHeader = "Edwards Car Wash Centre";
            String invoiceTitle = "Invoice";
            Date invoiceDate = new Date();

            // Table details
            float tableTopY = startY - 100;
            float tableBottomY = 150;
            float tableWidth = page.getMediaBox().getWidth() - (2 * startX);
            if (_debug) System.out.println("costing/generateCostPDF/invoice//tableWidth=" + tableWidth);
            float tableRowHeight = 20;
            int tableColumns = 3;
            // Table headers
            String[] tableHeaders = {"Item", "Description", "Price"};

            // Table data
            String[][] tableData = {
                    {"1", Description, String.valueOf(totalPrice)}
            };

            // Format decimal values
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            // Date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Draw invoice details
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(invoiceHeader);
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText(invoiceTitle);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Invoice Number: " + invoiceNumber);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Invoice Date: " + dateFormat.format(invoiceDate));
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Customer: " + customerName);
            contentStream.endText();

            // Specify the column widths
            float[] columnWidths = {50f, 350f, 100f}; //Adjust column widths

            // Draw table headers
            contentStream.setFont(pdfFont, 10);
            float tableX = startX;
            float tableY = tableTopY;

            for (int i = 0; i < tableHeaders.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, - 20);
                contentStream.newLineAtOffset(tableX, tableY);
                contentStream.showText(tableHeaders[i]);
                contentStream.endText();
                tableX += columnWidths[i];
            }

            // Draw table data
            contentStream.setFont(pdfFont, 10);
            tableY -= tableRowHeight;

            for (String[] rowData : tableData) {
                tableX = startX;

                for (int i = 0; i < rowData.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, - 20);
                    contentStream.newLineAtOffset(tableX, tableY);
                    contentStream.showText(rowData[i]);
                    contentStream.endText();
                    tableX += columnWidths[i];
                }

                tableY -= tableRowHeight;
            }


            // Draw total amount
            contentStream.setFont(pdfFont, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, tableY - 50);
            contentStream.showText("Total Amount: $" + decimalFormat.format(totalPrice));
            contentStream.endText();
            // Close the content stream
            contentStream.close();
            // Save the document to a file
            document.save(directory + invoiceNumber + ".pdf");
            // Close the document
            document.close();
            System.out.println("Invoice generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
