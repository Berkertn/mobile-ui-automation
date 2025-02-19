package org.mobile.steps.commons;

import io.cucumber.java.en.When;

public class CommonSteps extends StepDefinitionBase {

    @When("User is on the {word} from {string}")
    public void userSetPage(String pageName, String path) {
        iSetThePageAsFrom(pageName, path);
    }

    @When("User taps on the {word} field")
    public void userTapOnElement(String key) throws InterruptedException {
        iTapOnElement(key);
        System.out.println("Waiting for 15 seconds, start");
        Thread.sleep(15000);
        System.out.println("Waiting finished");
    }
}
