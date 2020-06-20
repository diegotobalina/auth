package com.spring.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** Class that runs at the start of the application */
@Slf4j
@Component
@AllArgsConstructor
public class Runner implements ApplicationRunner {
  @Override
  public void run(ApplicationArguments args) {}
}
