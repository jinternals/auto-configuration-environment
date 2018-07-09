package com.jinternals.logger.processor;

import com.jinternals.logger.annotation.InjectLogger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

import static org.junit.Assert.*;

public class LoggerInjectorBeanPostProcessorTest {

    private LoggerInjectorBeanPostProcessor processor;

    @Before
    public void setUp() throws Exception {
        processor = new LoggerInjectorBeanPostProcessor();
    }

    @Test
    public void shouldInjectLoggerBeforeInitialization() throws Exception {
        TestBean testBean = new TestBean();
        processor.postProcessBeforeInitialization(testBean, "myBeanBean");
        assertNotNull(testBean.logger);
    }

    @Test
    public void shouldNotInjectLoggerAfterInitialization() throws Exception {
        TestBean testBean = new TestBean();
        processor.postProcessAfterInitialization(testBean, "myBeanBean");
        Assert.assertEquals(null, testBean.logger);
    }


    @Test
    public void shouldReturnHighestOrder() throws Exception {
        Assert.assertEquals(Ordered.HIGHEST_PRECEDENCE, processor.getOrder());
    }

    class TestBean {
        @InjectLogger
        Logger logger;
    }

}