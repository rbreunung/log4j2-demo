# log4j2-demo

Review use cases how to append listener in log4j 2 for unit testing purpose.

- Configure log4j 2 using .xml configuration files.
- Configure log4j 2 using implemented configuration.

## Commands

### Run Main

```sh
mvn clean compile exec:java
```

### Run Tests

```sh
mvn test
```

## Lessons Learned

- old logger context must be closed before new one is created when [configuring by code](./src/test/java/de/antrophos/logging/LoggingConfigurationTest.java)
- logger must be created after the context
- both log4j2.xml test and main are named the same way so production code will not pick up [test configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html) on local test scenarios
- both log4j2.xml files do not get merged automatically
- root logger level is the initial setup for all name spaces
- static loggers may only be reached by an xml configuration or else must be set by a Powermock override.

## Notes

- <https://logging.apache.org/log4j/2.x/manual/customconfig.html>
- <https://logging.apache.org/log4j/2.x/manual/architecture.html>
- <https://logging.apache.org/log4j/2.x/manual/customconfig.html>
- <https://logging.apache.org/log4j/2.x/manual/appenders.html>
- <https://howtodoinjava.com/log4j2/configure-log4j2-for-junit/>
