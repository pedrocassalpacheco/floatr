/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.discovery;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 *
 * @author pedro.pacheco
 */
public class Session {

    InetAddress initialIP;
    InetAddress finalIP;
    InetAddress currentIP;
    String userName;
    String password;
    String controllerHost;
    Integer controllerPort;
    String controllerKey;

    public Session() {
    }

    public InetAddress getInitialIP() {
        return initialIP;
    }

    public void setInitialIP(InetAddress initialIP) {
        this.initialIP = initialIP;
    }

    public InetAddress getFinalIP() {
        return finalIP;
    }

    public void setFinalIP(InetAddress finalIP) {
        this.finalIP = finalIP;
    }

    public InetAddress getCurrentIP() {
        return currentIP;
    }

    public void setCurrentIP(InetAddress currentIP) {
        this.currentIP = currentIP;
    }

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

    public String getControllerHost() {
        return controllerHost;
    }

    public void setControllerHost(String controllerHost) {
        this.controllerHost = controllerHost;
    }

    public Integer getControllerPort() {
        return controllerPort;
    }

    public void setControllerPort(Integer controllerPort) {
        this.controllerPort = controllerPort;
    }

    public String getControllerKey() {
        return controllerKey;
    }

    public void setControllerKey(String controllerKey) {
        this.controllerKey = controllerKey;
    }

}
