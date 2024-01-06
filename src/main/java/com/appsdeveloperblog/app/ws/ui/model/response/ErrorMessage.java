package com.appsdeveloperblog.app.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

    private String errorMessage;
    private String errorMessageKey;
    private String href;

    public ErrorMessage(){
    }

    public ErrorMessage(String errorMessage, String errorMessageKey, String href) {
        this.errorMessage = errorMessage;
        this.errorMessageKey = errorMessageKey;
        this.href = href;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public void setErrorMessageKey(String errorMessageKey) {
        this.errorMessageKey = errorMessageKey;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
