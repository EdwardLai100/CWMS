/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.controller;

import com.edsproject.cwms.service.repositoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.edsproject.cwms.service.costing.generateCostPDF;
import com.edsproject.cwms.service.fileHandling.fileIO;
import com.edsproject.cwms.service.fileHandling.fileRemoveList;
import com.edsproject.cwms.service.flow.stateHandling;

import java.io.*;

@RestController
public class mainController {

    fileIO fileIO = new fileIO();
    fileRemoveList fileRemoveList = new fileRemoveList();
    stateHandling stateHandling = new stateHandling();
    generateCostPDF generateCostPDF = new generateCostPDF();
    private final repositoryService repositoryService;

    boolean _debug = true; //Next release to change it to read IO config for ease setting

    public mainController(repositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

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
    public ResponseEntity startQueue(@RequestParam(required = false, name = "startQueue") String item) throws IOException {
        fileIO.putItems("NewQueue", item + ",Queued");
        //Perform DB Persistence
        if (_debug) System.out.println("mainController/startQueue/item="+item);
        repositoryService.triggerQueueInsertion(item + ",Queued");
        return ResponseEntity.ok().body(""); //No return body needed because redirected to the same page (reloaded in jsp), applied to all same statement.
    }

    @PostMapping("/removeQueue")
    public ResponseEntity removeQueue(@RequestParam(required = false, name = "removeQueue") String numberPlate) {
        fileRemoveList.remove(numberPlate);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/flowManagement")
    public ModelAndView flowManagement() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("flowManagement");
        return modelAndView;
    }

    @PostMapping("/revertFlow")
    public ResponseEntity revertFlow(@RequestParam(required = false, name = "revertFlow") String numberPlate) throws IOException {
        if (_debug) System.out.println("com.edsproject.cwms/mainModule/revertFlow//numberPlate=" + numberPlate);
        stateHandling.stateChange("revertFlow", numberPlate);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/nextFlow")
    public ResponseEntity nextFlow(@RequestParam(required = false, name = "nextFlow") String numberPlate) throws IOException {
        if (_debug) System.out.println("com.edsproject.cwms/mainModule/nextFlow//numberPlate=" + numberPlate);
        stateHandling.stateChange("nextFlow", numberPlate);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/costManagement")
    public ModelAndView costManagement() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("costManagement");
        return modelAndView;
    }

    @PostMapping("/generateInvoice")
    public ResponseEntity<String> generateInvoice(@RequestParam(required = false, name = "generateInvoice") String numberPlate) throws IOException {
        generateCostPDF.invoice(fileIO.getItems(numberPlate));
        return ResponseEntity.ok().body("/download?numberPlate=" + numberPlate);
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam(required = false, name = "numberPlate") String numberPlate, HttpServletResponse response) throws IOException {
        fileIO.getGeneratedPDF(numberPlate,response);
    }
}
