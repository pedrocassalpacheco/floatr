/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.command.process;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class ps_ef extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(ps_ef.class.getName());

    public ps_ef() {
        super("ps", "-ef");
    }
}
