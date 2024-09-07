package com.backend.app.bootstrapper;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    plugin = "pretty",
    glue = {"com.backend.app.bootstrapper"}
)
public class TestRunner {
    
}
