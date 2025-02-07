package org.mobile.steps.commons;

import io.cucumber.java.en.When;

import static org.mobile.steps.commons.StepDefinitionBase.*;

public class CommonSteps {

    @When("User is on the {word} from {string}")
    public void userSetPage(String pageName, String path) {
        iSetThePageAsFrom(pageName, path);
    }

    @When("User taps on the {word} field")
    public void userTapOnElement(String key) {

    }
}
