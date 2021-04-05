/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.discovery;

import com.soickm.command.process.ps_aux_awk;
import com.soickm.commands.file.mkdir;
import com.soickm.commands.network.netstat_tpan_awk;
import com.soickm.exception.ConnectionException;
import com.soickm.remote.RemoteConnection;
import com.soickm.remote.RemoteCopy;
import com.soickm.remote.SSH;
import com.soickm.util.IPIterator;
import com.soickm.remote.SCP;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author pedro.pacheco
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private static final Session SESSION = new Session();

    public static void main(String[] args) throws Exception {

        LOG.log(Level.INFO, "------ Begin ----------");
        parseParameter(args);
        com.soickm.persistance.DocumentStorage.getInstance().connect("localhost", 9300);
        process();
        Thread.sleep(10000);
        LOG.log(Level.INFO, "------ End----------");

    }

    private static void process() throws ConnectionException {

        IPIterator iter = new IPIterator(SESSION.getInitialIP(), SESSION.getFinalIP());
        InetAddress ip = SESSION.getCurrentIP();
        while (iter.hasNext()) {
            SSH ssh = null;
            try {
                ssh = RemoteConnection.toLinux(ip, 22, SESSION.getUserName(), SESSION.getPassword());
                //ssh.execute(new mkdir("/opt/agents"));
                ssh.execute(new ps_aux_awk()).persistResultsTo(com.soickm.persistance.DocumentStorage.getInstance(), "Discovery", "Process");
                //ssh.execute(new ps_aux_awk()).persistResultsTo(LOG);

                ssh.execute(new netstat_tpan_awk()).persistResultsTo(com.soickm.persistance.DocumentStorage.getInstance(), "Discovery", "Network");
                //ssh.execute(new netstat_tpan_awk()).persistResultsTo(LOG);
                //ssh.persistRemoteConnection(com.soickm.persistance.DocumentStorage.getInstance(), "Discovery", "Connections", true);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error while trying to connect to host " + ip.getCanonicalHostName(), ex);
                //ssh.persistRemoteConnection(com.soickm.persistance.DocumentStorage.getInstance(), "Discovery", "Connections", false);
            } finally {
                if (ssh != null) {
                    ssh.disconnect();
                }
                ip = iter.next();
            }
        }
    }

    private static void parseParameter(String[] args) throws UnknownHostException, ParseException {

        // Create the command line parser
        CommandLineParser parser = new DefaultParser();

        // Create the Options
        Options options = new Options();
        options.addOption("help", "Print this message");
        options.addOption("f", "first ip on range", true, "First IP of search range");
        options.addOption("l", "last ip on range", true, "Last IP of search range");
        options.addOption("t", "timeout", true, "Timeout on ssh connection in ms");
        options.addOption("u", "username", true, "User account for all ssh connections");
        options.addOption("p", "password", true, "Password for all ssh connections");
        options.addOption("c", "config file", true, "Where to find the configuration file");

        // Parse the command line arguments
        CommandLine line = parser.parse(options, args);
        String initialIP = line.getOptionValue("f");
        String lastIP = line.getOptionValue("l");
        Integer timeout;
        timeout = Integer.parseInt(line.getOptionValue("t"));
        String configFile = line.getOptionValue("c");
        String userName = line.getOptionValue("u");
        String password = line.getOptionValue("p");

        LOG.log(Level.INFO, "Initial IP {0}:", initialIP);
        LOG.log(Level.INFO, "Last IP {0}:", lastIP);
        LOG.log(Level.INFO, "Network timeout {0}:", timeout);
        LOG.log(Level.INFO, "Config File {0}:", configFile);
        LOG.log(Level.INFO, "User name {0}:", userName);
        LOG.log(Level.INFO, "Passwprd {0}:", password.replaceAll("\\s\\S", "*"));

        SESSION.setInitialIP(InetAddress.getByName(initialIP));
        SESSION.setCurrentIP(SESSION.getInitialIP());
        SESSION.setFinalIP(InetAddress.getByName(lastIP));
        SESSION.setUserName(userName);
        SESSION.setPassword(password);

    }
}
