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
public class unzip extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(unzip.class.getName());

    public unzip(String from, String to) {
        super("unzip", from + " -d " + to);
    }
}

