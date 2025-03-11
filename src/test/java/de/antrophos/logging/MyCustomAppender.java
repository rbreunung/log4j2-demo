package de.antrophos.logging;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Plugin(name = "MyCustomAppender", category = "Core", elementType = Appender.ELEMENT_TYPE, printObject = true)
public class MyCustomAppender extends AbstractAppender {

    private final List<String> logMessages = new ArrayList<>();

    protected MyCustomAppender(String name, Layout<? extends Serializable> layout) {
        super(name, null, layout, false, null);
    }

    @Override
    public void append(LogEvent event) {
        logMessages.add(event.getMessage().getFormattedMessage());
    }

    public List<String> getLogMessages() {
        return logMessages;
    }

    @PluginFactory
    public static MyCustomAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new MyCustomAppender(name, layout);
    }
}