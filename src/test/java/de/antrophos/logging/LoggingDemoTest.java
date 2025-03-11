package de.antrophos.logging;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Before;
import org.junit.Test;

public class LoggingDemoTest {
    private static final Logger logger = LogManager.getLogger(LoggingDemo.class);
    private MyCustomAppender appender;

    @Before
    public void setUp() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        appender = config.getAppender("MyCustomAppender");
    }

    @Test
    public void testMain_loggerLevelInfo_messageLogged() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.INFO);

        LoggingDemo.main(null);

        List<String> logMessages = appender.getLogMessages();
        assertTrue(logMessages.contains("This is an info log message."));
    }

    @Test
    public void testMain_loggerLevelWarn_infoMessageNotLogged() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.WARN);
        appender.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender.getLogMessages();
        assertFalse(logMessages.contains("This is an info log message."));
    }

    @Test
    public void testMain_loggerLevelWarn_warnMessageLogged() {
        Configurator.setLevel(logger.getName(), org.apache.logging.log4j.Level.WARN);
        appender.getLogMessages().clear();

        LoggingDemo.main(null);

        List<String> logMessages = appender.getLogMessages();
        assertTrue(logMessages.contains("This is a warning log message."));
    }
}
