/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.remote;

import com.soickm.command.AbstractCommand;
import com.soickm.persistance.AbstractPersistance;
import java.net.ConnectException;
import java.util.logging.Level;

/**
 *
 * @author pedro.pacheco
 */
interface AbstractRemoteConnection {
    public void connect();
    public void disconnect();
    public AbstractCommand execute(String cmd);
    public AbstractCommand execute(AbstractCommand cmd);
    public Boolean persistRemoteConnection(AbstractPersistance persistance, Object... args) throws Exception;
}
