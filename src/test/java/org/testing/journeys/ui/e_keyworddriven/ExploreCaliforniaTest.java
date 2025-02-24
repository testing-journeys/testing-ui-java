package org.testing.journeys.ui.e_keyworddriven;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testing.journeys.ui.e_keyworddriven.extensions.JsonScenario;
import org.testing.journeys.ui.e_keyworddriven.extensions.JsonScenarioParameterResolver;
import org.testing.journeys.ui.e_keyworddriven.mappers.ActionMapper;
import org.testing.journeys.ui.e_keyworddriven.procedure.Scenario;

@ExtendWith(SeleniumJupiter.class)
class ExploreCaliforniaTest {

    @Test
    @JsonScenario("ExploreCalifornia.yml")
    @ExtendWith(JsonScenarioParameterResolver.class)
    void shouldFillInContactPageForm(Scenario scenario, ChromeDriver driver) {
        scenario.getSteps().forEach(step -> ActionMapper.execute(driver, step));
    }
}
