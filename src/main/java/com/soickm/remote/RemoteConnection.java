/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.remote;

import com.jcraft.jsch.*;
import java.net.InetAddress;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class RemoteConnection {

    private static final Logger LOG = Logger.getLogger(RemoteConnection.class.getName());
    private Session session;

    /**
     * Get the value of session
     *
     * @return the value of session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Set the value of session
     *
     * @param session new value of session
     */
    public void setSession(Session session) {
        this.session = session;
    }

    static public SSH toLinux(InetAddress host, Integer port, String user, String password) throws Exception {
        return new SSH(host, port, user, password);
    }

    static public SSH toLinux(String host, Integer port, String user, String password) throws Exception {
        return new SSH(InetAddress.getByName(host), port, user, password);
    }
    
}
