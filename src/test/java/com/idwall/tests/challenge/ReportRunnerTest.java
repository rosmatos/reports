package com.idwall.tests.challenge;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
        features={"src/test/features/"},
        glue = { "com.idwall.tests.challenge.steps", "com.idwall.tests.challenge.hooks"},
        plugin = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class ReportRunnerTest {

}
