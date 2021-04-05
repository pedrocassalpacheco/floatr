/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.exception;

import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class PersistanceException extends BaseException {
  public PersistanceException(Exception e, Logger LOG) {
        super(e.getMessage(), LOG);
    }    
}
