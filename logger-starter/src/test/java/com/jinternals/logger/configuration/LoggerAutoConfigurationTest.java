package com.jinternals.logger.configuration;

import static org.junit.Assert.*;

import com.jinternals.logger.processor.LoggerInjectorBeanPostProcessor;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import static org.junit.Assert.assertEquals;
import static org.springframework.util.Assert.notNull;

public class LoggerAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void shouldLoadLoggerInjectorBeanPostProcessor() throws Exception {
        load(EmptyConfiguration.class);
        LoggerInjectorBeanPostProcessor beanPostProcessor = this.context.getBean(LoggerInjectorBeanPostProcessor.class);
        notNull(beanPostProcessor, "The loggerInjectorBeanPostProcessor bean must not be null");
        assertEquals(Ordered.HIGHEST_PRECEDENCE, beanPostProcessor.getOrder());
        assertEquals(1, this.context.getBeansOfType(LoggerInjectorBeanPostProcessor.class).size());
    }

    @Test
    public void shouldLoadCustomLoggerInjectorBeanPostProcessor() throws Exception {
        load(CustomLoggerInjectorBeanPostProcessor.class);
        LoggerInjectorBeanPostProcessor beanPostProcessor = this.context.getBean(LoggerInjectorBeanPostProcessor.class);
        notNull(beanPostProcessor, "The loggerInjectorBeanPostProcessor bean must not be null");
        assertEquals(-1, beanPostProcessor.getOrder());
        assertEquals(1, this.context.getBeansOfType(LoggerInjectorBeanPostProcessor.class).size());
    }

    @Configuration
    static class EmptyConfiguration {
    }

    @Configuration
    static class CustomLoggerInjectorBeanPostProcessor {
        @Bean
        public LoggerInjectorBeanPostProcessor loggerInjectorBeanPostProcessor() {
            return new LoggerInjectorBeanPostProcessor() {
                @Override
                public int getOrder() {
                    return -1;
                }

            };
        }
    }

    private void load(Class<?> config, String... environment) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        EnvironmentTestUtils.addEnvironment(applicationContext, environment);
        applicationContext.register(config);
        applicationContext.register(LoggerAutoConfiguration.class);
        applicationContext.refresh();
        this.context = applicationContext;
    }
}