/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.persistance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.soickm.exception.ConnectionException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 *
 * @author pedro.pacheco
 */
public final class DocumentStorage extends AbstractPersistance {

    private static DocumentStorage instance;
    private Client client;
    private String host;
    private Integer port;
    private final ObjectMapper mapper;
    private static final Logger LOG = Logger.getLogger(DocumentStorage.class.getName());

    private DocumentStorage() {
        mapper = new ObjectMapper();
    }

    // Static block initialization for exception handling
    static {
        try {
            instance = new DocumentStorage();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static DocumentStorage getInstance() {
        return instance;
    }

    @Override
    public void connect(String hostName, Integer port) throws ConnectionException {

        if (client != null) {
            LOG.log(Level.WARNING, "Already connected to{0}:{1}", new Object[]{hostName, port});
        }

        if (StringUtils.isEmpty(hostName)) {
            throw new ConnectionException(new UnknownHostException(hostName), LOG);
        } else {
            setHost(hostName);
        }

        setPort(port);

        try {
            // Now establishes connection to Elastic Search
            client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), port));
        } catch (UnknownHostException ex) {
            throw new ConnectionException(new UnknownHostException(hostName), LOG);
        }

    }

    @Override
    public void disconnect() {

        if (client == null) {
            LOG.log(Level.WARNING, "Not connected to ElasticSearch");
            return;
        }

        client.close();

    }

    @Override
    public Boolean delete(String index, String collection, Object obj) {
        return true;
    }

    @Override
    public Boolean updated(String index, String collection, Object obj) {
        return true;
    }

    @Override
    public Boolean replace(String index, String collection, Object obj) {
        return true;
    }

    @Override
    public Boolean insert(String index, String collection, ObjectNode obj) throws ConnectionException {

        if (client == null) {
            throw new ConnectionException(new Exception("Not conneted"), LOG);
        }

        LOG.log(Level.INFO, "Storing {0} to {1}", new Object[]{obj.toString(), this.getClient().toString()});

        IndexResponse response = client.prepareIndex(index.toLowerCase(), collection.toLowerCase())
                .setSource(obj.toString())
                .get();

        return response.isCreated();
    }

    /**
     *
     * @param index
     * @param collection
     * @param objs
     * @return
     * @throws ConnectionException
     */
    @Override
    public Boolean insert(String index, String collection, ArrayNode objs) throws ConnectionException {

        if (client == null) {
            throw new ConnectionException(new Exception("Not conneted"), LOG);
        }

        LOG.log(Level.INFO, "Storing {0} to {1}", new Object[]{objs.toString(), this.getClient().toString()});

        BulkRequestBuilder bulkRequest; 
        bulkRequest = client.prepareBulk();
        for (final JsonNode obj : objs) {
           
            bulkRequest.add(client.prepareIndex(index.toLowerCase(), collection.toLowerCase())
                       .setSource(obj.toString())
            );
        }

        // Is there a response?
        if (bulkRequest != null) {
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.hasFailures()) {
                bulkResponse.forEach(response -> LOG.log(Level.SEVERE, response.getFailureMessage()));
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /*
     * Getters and Setter
     * 
     */
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
