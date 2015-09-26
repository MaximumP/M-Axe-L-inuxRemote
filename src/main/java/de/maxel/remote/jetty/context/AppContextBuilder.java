package de.maxel.remote.jetty.context;

import org.eclipse.jetty.webapp.WebAppContext;

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
    public WebAppContext buildWebAppContext() {

        WebAppContext context = new WebAppContext();
        context.setDescriptor(context + "/WEB-INF/web.xml");
        context.setResourceBase(".");
        context.setContextPath("/maxel");
        return context;
    }
}
