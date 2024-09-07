package com.backend.app.bootstrapper;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.backend.app.boostrapper")
@EnableAutoConfiguration
public class CheckControllerTestConfig {
    
}
