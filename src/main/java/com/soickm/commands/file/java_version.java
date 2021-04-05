/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.commands.file;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class java_version extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(java_version.class.getName());

    public java_version() {
        //super("java", "-version 2>&1 > /dev/nul | awk '{printf(\"{\\\"version\\\":\\\"%s\\\"}\\n\",$0)}'");
        super("java", "-version 2>&1 > /dev/nul | awk '{printf(\"{version:%s}\\n\",$0)}'");

    }
    
    public String getJavaVersion() {
        
        // On this a single response
        return getSingleResult().findValue("version").asText();
    }
        
}

