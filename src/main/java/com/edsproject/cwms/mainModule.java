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
 * 14 JUNE 2023      EDWARD LAI           1.3.1       CODE RESTRUCTURE & ENHANCE
 * 2 JULY 2023       EDWARD LAI           1.4         IMPLEMENT JPA HIBERNATE DATABASE RECORDS FUNCTION,
 *                                                    CODE RESTRUCTURE & ENHANCE
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.edsproject.cwms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.edsproject.cwms")
public class mainModule {

    public static void main(String[] args) {
        SpringApplication.run(mainModule.class, args);
    }

}
