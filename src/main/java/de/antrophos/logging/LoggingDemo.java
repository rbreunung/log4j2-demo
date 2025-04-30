package de.antrophos.logging;

import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_TIME;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingDemo {
    // we cannot use static logger if we reconfigure during runtime
    // private static final Logger logger = LogManager.getLogger(LoggingDemo.class);

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(LoggingDemo.class);

        logger.info("This is an info log message at {}.",ISO_OFFSET_TIME.format(now()));
        logger.warn("This is a warning log message.");
        logger.error("This is an error log message.");
        logger.fatal("This is a fatal message", new RuntimeException("This is the outer Exception", new RuntimeException("This is the inner Exception")));
    }
}