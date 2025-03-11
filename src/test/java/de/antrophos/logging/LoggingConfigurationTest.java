package de.antrophos.logging;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoggingConfigurationTest {
    private Logger logger;
    private MyCustomAppender appender;
    private LoggerContext loggerContext;

    @Before
    public void setUp() {
        ((LoggerContext) LogManager.getContext(false)).close();
        ConfigurationBuilder<BuiltConfiguration> configBuilder = ConfigurationBuilderFactory.newConfigurationBuilder();
        configBuilder = configBuilder.add(
                configBuilder.newAppender("MyCustomAppender", "MyCustomAppender")
                        .add(configBuilder.newLayout("PatternLayout").addAttribute("pattern", "%m%n")));
        configBuilder = configBuilder
                .add(configBuilder.newRootLogger(Level.ERROR).add(configBuilder.newAppenderRef("MyCustomAppender")));
        BuiltConfiguration config = configBuilder.build(true);

        loggerContext = Configurator.initialize(config);
        logger = LogManager.getLogger(LoggingDemo.class);
        
        appender = config.getAppender("MyCustomAppender");
    }

    @After
    public void tearDown() {
        loggerContext.close();
        loggerContext = null;
        appender = null;
        logger = null;
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
