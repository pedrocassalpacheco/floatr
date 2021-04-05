/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.commands.network;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class netstat_tpan_awk extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(netstat_tpan_awk.class.getName());

    public netstat_tpan_awk() {
        super("netstat", "-tpan | awk '{if(NR>2)printf(\"{\\\"protocol\\\":\\\"%s\\\", \\\"recv-q\\\":\\\"%s\\\", \\\"send-q\\\":\\\"%s\\\", \\\"local-addr\\\":\\\"%s\\\", \\\"remote-addr\\\":\\\"%s\\\", \\\"state\\\":\\\"%s\\\", \\\"pid-programname\\\":\\\"%s\\\"}\\n\",$1,$2,$3,$4,$5,$6,$7)}'");
    }     
}
