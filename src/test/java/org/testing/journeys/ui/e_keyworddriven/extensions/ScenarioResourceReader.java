package org.testing.journeys.ui.e_keyworddriven.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.testing.journeys.ui.e_keyworddriven.procedure.Scenario;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

public final class ScenarioResourceReader {

    static Scenario readScenarioFromResourcePath(String scenarioPath) {
        if (scenarioPath.endsWith(".json")) {
            return readJsonScenarioFromResourcePath(scenarioPath);
        }
        if (scenarioPath.endsWith(".yml")) {
            return readYmlScenarioFromResourcePath(scenarioPath);
        }
        throw new RuntimeException("Failed to load Scenario file: " + scenarioPath);
    }


    static Scenario readYmlScenarioFromResourcePath(String scenarioPath) {
        Yaml yaml = new Yaml(new Constructor(Scenario.class, new LoaderOptions()));
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(scenarioPath)) {
            return yaml.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Scenario readJsonScenarioFromResourcePath(String scenarioPath) {
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
