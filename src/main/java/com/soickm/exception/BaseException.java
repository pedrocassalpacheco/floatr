/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class BaseException extends Exception {
    
    public BaseException(String message, Logger LOG) {
        LOG.log(Level.SEVERE, message);
    }
}
