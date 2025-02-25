package org.testing.journeys.ui.f_processdriven;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "org.testing.journeys.ui.f_processdriven.definition",
        features = "src/test/resources/features")
public class ExploreCaliforniaTest {
}
