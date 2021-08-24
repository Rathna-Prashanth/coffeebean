package io.coffeebean.logging;

import io.coffeebean.logging.profiler.EventLogs;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;

public interface LoggingHandler {

    public static LoggingHandler initialize() {
        try {
            EventLogs.initialize();
        } catch (IOException e) {
            EventLogs.log(e.toString());
        } catch (XmlPullParserException e) {
            EventLogs.log(e.toString());
        }
        return new EventLogs();
    }
}
