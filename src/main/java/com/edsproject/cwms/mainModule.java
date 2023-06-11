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
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.edsproject.cwms;

import com.edsproject.cwms.fileHandling.fileIO;
import com.edsproject.cwms.fileHandling.fileRemoveList;
import com.edsproject.cwms.flow.stateHandling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class mainModule {

    fileIO fileIO = new fileIO();
    fileRemoveList fileRemoveList = new fileRemoveList();
    stateHandling stateHandling = new stateHandling();
    boolean _debug = true; //Next release to change it to read IO config for ease setting

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
            fileIO.putItems("NewQueue",item + ",Queued");
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
    }

}
