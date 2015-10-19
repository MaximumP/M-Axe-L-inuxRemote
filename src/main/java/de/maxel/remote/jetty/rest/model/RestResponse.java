package de.maxel.remote.jetty.rest.model;

/**
 * Created by max on 16.10.15.
 */
public class RestResponse {

    private ResponseState responseState;
    private String message;
    private Object model;

    public void setResponseState(ResponseState responseState){
        this.responseState = responseState;
    }

    public ResponseState getResponseState() {
        return responseState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public enum ResponseState{
        Success,
        Warning,
        Error
    }
}
