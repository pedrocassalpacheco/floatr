/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.remote;

import com.soickm.command.AbstractCommand;
import com.soickm.persistance.AbstractPersistance;
import java.net.InetAddress;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author pedro.pacheco
 */
public class WMI implements AbstractRemoteConnection {

    private static final Logger LOG = Logger.getLogger(WMI.class.getName());

    public WMI() {
    }

    public WMI(InetAddress host, Integer port, String user, String password) throws Exception {

    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.      
    }

    @Override
    public void connect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractCommand execute(String cmd) {
        return null;
    }

    @Override
    public AbstractCommand execute(AbstractCommand cmd) {
        execute(cmd.buildCommand());
        return cmd;
    }

    @Override
    public Boolean persistRemoteConnection(AbstractPersistance persistance, Object... args) {
       return true;
    }
}
