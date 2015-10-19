package de.maxel.remote.jetty.server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Created by max on 18.09.15.
 *
 * Creates the context for the Jetty server
 */
public class AppContextBuilder {

    /**
     * Creates the WebAppContext for the jetty server
     * @return Context used by jetty
     */
    public ServletContextHandler buildWebAppContext() {
        ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
        servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "de.maxel.remote.jetty.rest");
        servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(servletHolder, "/maxel/*");

        return contextHandler;
    }
}
