package com.jinternals.environment.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import static java.lang.Boolean.parseBoolean;
import static java.util.Objects.isNull;

public class LocalPropertiesPostProcessor implements EnvironmentPostProcessor {

    private static final String LOCAL_CONFIG = "localConfig";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication springApplication) {

        String localPropertiesEnabled = environment.getProperty("local.properties.enabled");
        String localPropertiesLocation = environment.getProperty("local.properties.location");
        Resource resource = new FileSystemResource(localPropertiesLocation);

        try {
            if (isAllowedToLoadDevProperties(localPropertiesEnabled, resource)) {
                PropertySource<?> propertySource = new PropertiesPropertySourceLoader()
                        .load(LOCAL_CONFIG, resource, null);
                environment.getPropertySources().addLast(propertySource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAllowedToLoadDevProperties(String allToggleProperty, Resource resource) {
        return !isNull(allToggleProperty) && parseBoolean(allToggleProperty) && resource.exists();
    }

}
