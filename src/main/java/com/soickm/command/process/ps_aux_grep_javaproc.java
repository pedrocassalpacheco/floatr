/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.command.process;
import com.soickm.persistance.AbstractPersistance;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class ps_aux_grep_javaproc extends com.soickm.command.AbstractCommand {

    private static final Logger LOG = Logger.getLogger(ps_aux_grep_javaproc.class.getName());

    public ps_aux_grep_javaproc() {
        super("ps", "aux | awk '{if(NR>1)printf(\"{\\\"User\\\":\\\"%s\\\", \\\"PID\\\":\\\"%s\\\", \\\"CPU\\\":\\\"%s\\\", \\\"Memory\\\":\\\"%s\\\", \\\"VSZ\\\":\\\"%s\\\", \\\"RSS\\\":\\\"%s\\\", \\\"TTY\\\":\\\"%s\\\", \\\"STAT\\\":\\\"%s\\\", \\\"Start\\\":\\\"%s\\\", \\\"Time\\\":\\\"%s\\\", \\\"Command\\\":\\\"%s\\\"}\\n\",$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11)}'");
    }     
}
