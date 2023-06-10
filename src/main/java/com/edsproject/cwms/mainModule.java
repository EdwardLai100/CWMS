/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * CHANGE HISTORY
 * DATE              AUTHOR                    DESCRIPTION
 * 8 JUNE 2023       EDWARD LAI                INITIAL RELEASE
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.edsproject.cwms;

import com.edsproject.cwms.config.fileOutput;
import com.edsproject.cwms.config.fileRemoveList;
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

    fileOutput fileoutput = new fileOutput();
    fileRemoveList fileremovelist = new fileRemoveList();

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
            fileoutput.output(item + ",Queued");
            return new ModelAndView("queueManagement");
        }

        @PostMapping("/removeQueue")
        public ModelAndView removeQueue(@RequestParam(required = false, name = "removeQueue") String numberPlate) {
            fileremovelist.remove(numberPlate);
            return new ModelAndView("queueManagement");
        }
    }

}