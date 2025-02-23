package org.testing.journeys.ui.f_processdriven.definition;

import cucumber.api.java.After;
import org.testing.journeys.ui.f_processdriven.adapter.driver.DriverFactory;

public class Hooks {

    @After
    public void tearDown() {
        DriverFactory.getInstance().closeSession();
    }
}
