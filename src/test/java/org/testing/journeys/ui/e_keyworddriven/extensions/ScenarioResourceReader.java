package org.testing.journeys.ui.e_keyworddriven.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.testing.journeys.ui.e_keyworddriven.procedure.Scenario;

import java.io.IOException;
import java.io.InputStream;

public final class ScenarioResourceReader {

    static Scenario readScenarioFromResourcePath(String scenarioPath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(scenarioPath)) {
                if (inputStream == null) {
                    throw new IOException("Resource not found: " + scenarioPath);
                }
                return mapper.readValue(inputStream, Scenario.class);
            }
        } catch (IOException e) {
            throw new ParameterResolutionException("Exception encountered while reading/parsing the scenario file: "
                    + e.getMessage());
        }
    }
}
