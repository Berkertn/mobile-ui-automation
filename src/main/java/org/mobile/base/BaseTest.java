package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected AppiumDriver driver;

    @BeforeAll
    static void projectSetUp() {
        AppiumServerManager.startServer();
    }

    @BeforeEach
    public void setUpTestCase() {
        driver = DriverManager.getDriver();
    }

    @AfterEach
    public void tearDownTestCase() {
        DriverManager.quitDriver();
    }

    @AfterAll
    static void projectTearDown() {
        AppiumServerManager.stopServer();
    }
}
