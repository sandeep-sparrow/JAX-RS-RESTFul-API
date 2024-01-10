package com.appsdeveloperblog.app.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

/*
 *
 * @author: Sandeep prajapati
 *
 */
@XmlRootElement
public class LoginCredentials {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
