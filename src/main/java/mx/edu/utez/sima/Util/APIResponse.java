package mx.edu.utez.sima.Util;

import org.springframework.http.HttpStatus;


public class APIResponse {
    private String message;
    private Object data;
    private Boolean error;
    private HttpStatus status;

    public APIResponse(String message, Boolean error, HttpStatus status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }

    public APIResponse(String message, Object data, Boolean error, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.error = error;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
