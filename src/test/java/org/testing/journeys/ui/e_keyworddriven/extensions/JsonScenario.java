package org.testing.journeys.ui.e_keyworddriven.extensions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonScenario {

    String value();
}
