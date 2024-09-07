package com.backend.app.bootstrapper;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.backend.app.boostrapper.CheckController;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
@SpringBootTest
@ContextConfiguration(classes = CheckControllerTestConfig.class)
public class CheckControllerTest {
    
    @Autowired
    private CheckController checkController;

    String expected;

    @Given("I have postmand")

    public void i_have_postmand() {
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
        System.out.println("I have postmand");
    }

    @When("I send reqeust")
    public void i_send_reqeust() {
        // Write code here that turns the phrase above into concrete actions
        expected = checkController.check();
        //throw new cucumber.api.PendingException();
    }

    @Then("I receive message \"Working\"")
    public void i_receive_message_working() {
        assertEquals(expected, "Working");
        // Write code here that turns the phrase above into concrete actions
        //throw new cucumber.api.PendingException();
    }
}
