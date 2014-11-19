package com.moto.agent.proxy.mina.message;

/**
 * Created by Eric on 10/22/14.
 */
public class SnmpConnectMessage extends AbstractMessage {

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

    @Override
    public String toString() {
        return "SnmpConnectMessage{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
