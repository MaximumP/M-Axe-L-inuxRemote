package de.maxel.remote.jetty.context;

import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by max on 18.09.15.
 */
public class AppContextBuilder {

    private WebAppContext context;

    /**
     * Creates the WebAppContext for the jetty server
     * @return Context used by jetty
     */
    public WebAppContext buildWebAppContext() {

        context = new WebAppContext();
        context.setDescriptor(context + "/web/WEB-INF/web.xml");
        context.setResourceBase(".");
        context.setContextPath("/maxel");
        return context;
    }
}
