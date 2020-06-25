package com.spring.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/** @author diegotobalina created on 24/06/2020 */
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
