/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.commands.file;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

/**
 *
 * @author pedro.pacheco
 */
public class which extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(which.class.getName());

    public which(String cmd) {
        super("which", cmd +" | awk '{printf(\"{\\\"path\\\":\\\"%s\\\"}\\n\",$1)}'");

    }
    
    public String getCommandPath() {
        
        // On this a single response
        return getSingleResult().findValue("path").asText();
    }
        
}

