/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.soickm.persistance.AbstractPersistance;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public abstract class AbstractCommand {

    private static final Logger LOG = Logger.getLogger(AbstractCommand.class.getName());

    private InetAddress host;
    private Instant timeStampCollector;
    private String command;
    private ArrayList<String> params;
    private String filter;
    private final ArrayNode results;

    public AbstractCommand(String command, ArrayList<String> params) {
        this.command = command;
        this.params = params;
        this.results = JsonNodeFactory.instance.arrayNode();
    }

    public AbstractCommand(String command, String params) {
        this.command = command;
        this.params = new ArrayList<>();
        this.addParameter(params);
        this.results = JsonNodeFactory.instance.arrayNode();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public InetAddress getHost() {
        return host;
    }

    public void setHost(InetAddress host) {
        this.host = host;
    }

    public void setHost(String host) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
    }

    public Instant getTimeStamp() {
        return timeStampCollector;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStampCollector = timeStamp;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    /**
     * This is needs to be optimized
     *
     * @param line
     * @throws java.io.IOException
     */
    public void addResult(String line) {

        // Transforms string JSON into JSON
        ObjectMapper mapper = new ObjectMapper();     
        ObjectNode json;
        
        try { 
            json = (ObjectNode) mapper.readTree(line);
            json.put("host", getHost().getHostAddress());
            json.put("ts", Instant.now().toString());
            json.put("cmd", getCommand());
            results.add(json);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Unable to parse result {0}. \n Underlying exception: {1}", new Object[] { line, ex.getMessage() } );
        }
    }
    
    public String getResultAsString() {
        return results.get(0).toString();
    }
    
    public JsonNode getSingleResult() {
        return results.get(0);
    }

    public final void addParameter(String parameter) {
        if (this.params == null) {
            this.params = new ArrayList<>();
        }
        this.params.add(parameter);
    }

    public String buildCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        sb.append(' ');
        params.forEach((i) -> sb.append(i).append(" "));
        return sb.toString();
    }

    public void persistResultsTo(AbstractPersistance persistance, Object... args) throws Exception {

        LOG.log(Level.INFO, "Persisting results of {0}", this.command);

        Boolean create;
        create = persistance.insert(args[0].toString(), args[1].toString(), results);
        if (create != true) {
            LOG.log(Level.INFO, "Results not persisted");
        }

    }

    /**
     *
     * @param log
     * @throws Exception
     */
    public void persistResultsTo(Logger log) throws Exception {
        results.forEach(json -> {
            log.log(Level.INFO, json.toString());
        });
    }
}
