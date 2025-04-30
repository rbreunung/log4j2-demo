package de.antrophos.logging;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoggingDemoAdditionalAppenderTest {
    private final Logger logger = LogManager.getLogger(LoggingDemo.class);
    private MyCustomAppender appender1;
    private MyCustomAppender appender2;

    @Before
    public void setUp() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        appender1 = config.getAppender("MyCustomAppender");
        appender2 = MyCustomAppender.createAppender("OtherAppender", PatternLayout.createDefaultLayout());
        appender2.start();
        config.addAppender(appender2);
        context.getRootLogger().addAppender(appender2);
        context.updateLoggers();
    }

    @After
    public void tearDown() {
        ((LoggerContext) LogManager.getContext(false)).close();
        appender1 = null;
        appender2.stop();
        appender2 = null;
    }

    @Test
    public void testMain_loggerLevelInfo_messageLoggedAppender1() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.INFO);
        appender1.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender1.getLogMessages();
        assertTrue(logMessages.contains("This is an info log message."));
    }

    @Test
    public void testMain_loggerLevelInfo_messageLoggedAppender2() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.INFO);
        appender2.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender2.getLogMessages();
        assertTrue(logMessages.contains("This is an info log message."));
    }

    @Test
    public void testMain_loggerLevelWarn_infoMessageNotLoggedAppender1() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.WARN);
        appender1.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender1.getLogMessages();
        assertFalse(logMessages.contains("This is an info log message."));
    }

    @Test
    public void testMain_loggerLevelWarn_infoMessageNotLoggedAppender2() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.WARN);
        appender2.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender2.getLogMessages();
        assertFalse(logMessages.contains("This is an info log message."));
    }

    @Test
    public void testMain_loggerLevelWarn_warnMessageLoggedAppender1() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.WARN);
        appender1.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender1.getLogMessages();
        assertTrue(logMessages.contains("This is a warning log message."));
    }

    @Test
    public void testMain_loggerLevelWarn_warnMessageLoggedAppender2() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.WARN);
        appender2.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender2.getLogMessages();
        assertTrue(logMessages.contains("This is a warning log message."));
    }
}
