/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * CHANGE HISTORY
 * DATE              AUTHOR               VERSION     DESCRIPTION
 * 8 JUNE 2023       EDWARD LAI           1.0         INITIAL RELEASE
 * 11 JUNE 2023      EDWARD LAI           1.1         ADD COSTING FEATURE, CODE RESTRUCTURE, ENHANCE FILE IO
 * 11 JUNE 2023      EDWARD LAI           1.2         ADD FLOW MANAGEMENT, CODE RESTRUCTURE & ENHANCE
 * 11 JUNE 2023      EDWARD LAI           1.3         ADD COST MANAGEMENT AND PDF GENERATOR
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.edsproject.cwms;

import com.edsproject.cwms.costing.generateCostPDF;
import com.edsproject.cwms.fileHandling.fileIO;
import com.edsproject.cwms.fileHandling.fileRemoveList;
import com.edsproject.cwms.flow.stateHandling;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@SpringBootApplication
@RestController
public class mainModule {

    fileIO fileIO = new fileIO();
    fileRemoveList fileRemoveList = new fileRemoveList();
    stateHandling stateHandling = new stateHandling();
    generateCostPDF generateCostPDF = new generateCostPDF();
    boolean _debug = false; //Next release to change it to read IO config for ease setting

    public static void main(String[] args) {
        SpringApplication.run(mainModule.class, args);
    }

    @RestController
    public class HomeController {

        @GetMapping("/")
        public ModelAndView home() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("home");
            return modelAndView;
        }

        @GetMapping("/queueManagement")
        public ModelAndView queueManagement() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("queueManagement");
            return modelAndView;
        }

        @PostMapping("/startQueue")
        public ModelAndView startQueue(@RequestParam(required = false, name = "startQueue") String item) {
            fileIO.putItems("NewQueue", item + ",Queued");
            return new ModelAndView("queueManagement");
        }

        @PostMapping("/removeQueue")
        public ModelAndView removeQueue(@RequestParam(required = false, name = "removeQueue") String numberPlate) {
            fileRemoveList.remove(numberPlate);
            return new ModelAndView("queueManagement");
        }

        @GetMapping("/flowManagement")
        public ModelAndView flowManagement() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("flowManagement");
            return modelAndView;
        }

        @PostMapping("/revertFlow")
        public ModelAndView revertFlow(@RequestParam(required = false, name = "revertFlow") String numberPlate) {
            if (_debug) System.out.println("com.edsproject.cwms/mainModule/revertFlow//numberPlate=" + numberPlate);
            stateHandling.stateChange("revertFlow", numberPlate);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("flowManagement");
            return modelAndView;
        }

        @PostMapping("/nextFlow")
        public ModelAndView nextFlow(@RequestParam(required = false, name = "nextFlow") String numberPlate) {
            if (_debug) System.out.println("com.edsproject.cwms/mainModule/nextFlow//numberPlate=" + numberPlate);
            stateHandling.stateChange("nextFlow", numberPlate);
            //insert back to file output
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("flowManagement");
            return modelAndView;
        }

        @GetMapping("/costManagement")
        public ModelAndView costManagement() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("costManagement");
            return modelAndView;
        }

        @PostMapping("/generateInvoice")
        public ResponseEntity<String> generateInvoice(@RequestParam(required = false, name = "generateInvoice") String numberPlate) throws IOException {
            // insert back to file output
            generateCostPDF.invoice(fileIO.getItems(numberPlate));
            String fileUrl = "/download?numberPlate=" + numberPlate;
            return ResponseEntity.ok().body(fileUrl);
        }

        @GetMapping("/download")
        public void downloadFile(@RequestParam(required = false, name = "numberPlate") String numberPlate, HttpServletResponse response) throws IOException {
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
            } else {
            }
        }
    }
}
