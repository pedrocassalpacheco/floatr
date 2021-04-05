/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.command;
import com.soickm.command.process.*;
import com.soickm.persistance.AbstractPersistance;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class BasicCommand extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(BasicCommand.class.getName());

    public BasicCommand(String command, String args) {
        super(command, args);
    }
    

}
