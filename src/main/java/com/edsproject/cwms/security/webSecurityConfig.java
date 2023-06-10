/** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                       CHANGE HISTORY
 * DATE              AUTHOR                    DESCRIPTION
 * 8 JUNE 2023       EDWARD LAI                INITIAL RELEASE
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.edsproject.cwms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
public class webSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/startQueue", "/removeQueue");
    }

}
