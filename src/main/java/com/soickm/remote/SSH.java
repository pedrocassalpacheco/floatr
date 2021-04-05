/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.net.ConnectException;
import java.time.Instant;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import com.soickm.command.AbstractCommand;
import com.soickm.command.BasicCommand;
import com.soickm.exception.ConnectionException;
import com.soickm.persistance.AbstractPersistance;
import java.io.IOException;

/**
 *
 * @author pedro.pacheco
 */
public class SSH implements AbstractRemoteConnection {

    private static final Logger LOG = Logger.getLogger(SSH.class.getName());
    private static final Integer TIMEOUT = 10000; // TODO: This has to be configurable
    private Session session;

    public SSH() {
    }

    public SSH(InetAddress host, Integer port, String user, String password) throws ConnectionException, ConnectException {
        try {

            LOG.log(Level.INFO, "Logging into {0}", host);

            JSch jsch = new JSch();
            String hostAsString = host.getHostAddress();
            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");

            session = jsch.getSession(user, hostAsString, port);
            session.setPassword(password);
            session.setConfig(config);
            session.connect(TIMEOUT);

            LOG.log(Level.INFO, "Connected to host {0}", host);

        } catch (JSchException ex) {
            LOG.log(Level.SEVERE, "Unable to conntect to {0}", host);
            LOG.log(Level.SEVERE, "Exception", ex);
            throw new ConnectException(ex.getMessage());
        }
    }

    @Override
    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
    }

    @Override
    public void connect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param cmd
     * @return
     */
    @Override
    public AbstractCommand execute(AbstractCommand cmd) {
        if (session != null && session.isConnected()) {

            try {
                cmd.setHost(session.getHost());
            } catch (UnknownHostException ex) {
                Logger.getLogger(SSH.class.getName()).log(Level.SEVERE, null, ex);
            }

            cmd.setTimeStamp(Instant.now());

            ChannelExec channel;
            try {
                channel = (ChannelExec) session.openChannel("exec");
            } catch (JSchException ex) {
                Logger.getLogger(SSH.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

            String command = cmd.buildCommand();
            LOG.log(Level.INFO, "Command:{0}", command);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()))) {
                channel.setCommand(command);
                channel.connect();
                try (Stream<String> stream = in.lines()) {
                    stream.forEach(cmd::addResult);
                    stream.close();
                }
                in.close();

            } catch (JSchException | IOException ex) {
                LOG.log(Level.SEVERE, "Unable to conntect to execute {0}", command);
                LOG.log(Level.SEVERE, "Exception", ex);
            } finally {
                channel.disconnect();
            }
        }
        return cmd;
    }

    @Override
    public AbstractCommand execute(String command) {
        BasicCommand cmd = new BasicCommand(command, "/");
        return execute(cmd);
    }

    @Override
    public Boolean persistRemoteConnection(AbstractPersistance persistance, Object... args) throws Exception {
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        json.put("host", session.getHost());
        json.put("timestamp", Instant.now().toString());
        json.put("connection", "ssh");
        json.put("status", (Boolean) args[2]);

        return persistance.insert(args[0].toString(), args[1].toString(), json);

    }
}
