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
public class RemoteCopy {

    private static final Logger LOG = Logger.getLogger(RemoteCopy.class.getName());
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

    static public SCP toLinux(InetAddress host, Integer port, String user, String password, String from, String to) throws Exception {
        return new SCP(host, port, user, password, from, to);
    }

    static public SCP toLinux(String host, Integer port, String user, String password, String from, String to) throws Exception {
        return new SCP(InetAddress.getByName(host), port, user, password, from, to);
    }
    
}
