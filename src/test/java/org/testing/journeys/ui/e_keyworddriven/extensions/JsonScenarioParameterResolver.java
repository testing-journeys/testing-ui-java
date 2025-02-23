package org.testing.journeys.ui.e_keyworddriven.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import org.testing.journeys.ui.e_keyworddriven.procedure.Scenario;

import java.util.Optional;

import static org.testing.journeys.ui.e_keyworddriven.extensions.ScenarioResourceReader.readScenarioFromResourcePath;

public class JsonScenarioParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Scenario.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        Optional<JsonScenario> scenarioAnnotation =
                AnnotationSupport.findAnnotation(extensionContext.getTestMethod(), JsonScenario.class);

        if (scenarioAnnotation.isEmpty())
            throw new IllegalStateException(
                    "Please provide a scenario resource using @JsonScenario test annotation");

        return readScenarioFromResourcePath(scenarioAnnotation.get().value());
    }
}
