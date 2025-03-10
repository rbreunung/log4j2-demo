package de.antrophos.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingDemo {
    private static final Logger logger = LogManager.getLogger(LoggingDemo.class);

    public static void main(String[] args) {
        logger.info("This id an info log message.");
        logger.warn("This is a warning log message.");
        logger.error("This is an error log message.");
    }
}
