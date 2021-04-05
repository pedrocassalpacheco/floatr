/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.persistance;

import com.soickm.exception.ConnectionException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
/**
 *
 * @author pedro.pacheco
 */

public abstract class AbstractPersistance {
    
     public void connect(String host, Integer port) throws ConnectionException {};
     public void disconnect() {};
     public Boolean insert (String index, String docType, ObjectNode obj) throws Exception { return true; };
     public Boolean insert (String index, String docType, ArrayNode obj) throws Exception { return true; };
     public Boolean replace(String index, String docType, Object obj) throws Exception { return true; };
     public Boolean updated(String index, String docType, Object obj) throws Exception { return true; };
     public Boolean delete (String index, String docType, Object obj) throws Exception { return true; };
    
}
