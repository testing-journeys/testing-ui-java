package org.testingsol.tas.e_keyworddriven;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testingsol.tas.e_keyworddriven.extensions.JsonScenario;
import org.testingsol.tas.e_keyworddriven.extensions.JsonScenarioParameterResolver;
import org.testingsol.tas.e_keyworddriven.mappers.ActionMapper;
import org.testingsol.tas.e_keyworddriven.procedure.Scenario;

@ExtendWith(SeleniumJupiter.class)
class ExploreCaliforniaTest {

    @Test
    @JsonScenario("ExploreCalifornia.json")
    @ExtendWith(JsonScenarioParameterResolver.class)
    void shouldFillInContactPageForm(Scenario scenario, ChromeDriver driver) {
        scenario.getSteps().forEach(step -> ActionMapper.execute(driver, step));
    }
}
