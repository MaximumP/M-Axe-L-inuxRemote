package de.maxel.remote.jetty.rest.model;

/**
 * Created by max on 16.10.15.
 *
 * Contains information to the state of the request
 * Optionally a message and the a response model
 */
public class RestResponse {

    private ResponseState responseState;
    private String message;
    private Object model;

    /**
     * sets the state of a response
     *
     * @param responseState the state of the response
     */
    public void setResponseState(ResponseState responseState){
        this.responseState = responseState;
    }

    /**
     * gets the response state. Is called by the jaxb framework
     *
     * @return the state of the response
     */
    public ResponseState getResponseState() {
        return responseState;
    }

    /**
     * gets the response message. Is called by the jaxb framework
     *
     * @return the message of the response
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the response message
     *
     * @param message the message of the response
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gets the response model. Is called by the jaxb framework
     *
     * @return
     */
    public Object getModel() {
        return model;
    }

    /**
     * sets the response model.
     *
     * @param model the model to be sent to the client
     */
    public void setModel(Object model) {
        this.model = model;
    }

    /**
     * Defines the states for a response
     */
    public enum ResponseState{
        Success,
        Warning,
        Error
    }
}
