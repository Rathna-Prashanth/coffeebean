package io.coffeebean.logging.profiler;


import io.coffeebean.logging.LoggingHandler;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

public final class EventLogs implements LoggingHandler {
    public static Model model;

    public static LoggingHandler initialize() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        model = reader.read(new FileReader("pom.xml"));
        System.out.println("CoffeeBean V" + model.getVersion());
        return new EventLogs();
    }

    public static void log(String logDetail) {
        System.out.println(logDetail);
    }
}
