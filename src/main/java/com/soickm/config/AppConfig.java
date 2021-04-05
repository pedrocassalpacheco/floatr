/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.config;

import java.io.File;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class AppConfig {

    private static final Logger LOG = Logger.getLogger(AppConfig.class.getName());
    private static final Integer READINTERVAL = 5000;
    static {
        new Thread(() -> {
            while (true) {
                yamlReader();
                try {
                    Thread.sleep(READINTERVAL);
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public static Integer GetSSHTimeOut() {
        return 0;
    }
   
    public static void yamlReader() {
        System.out.println("Reading yaml ...");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
Path currentRelativePath = Paths.get("");
String s = currentRelativePath.toAbsolutePath().toString();
System.out.println("Current relative path is: " + s);
            Configuration discovery = mapper.readValue(new File("conf/config.yml"), Configuration.class);
            LOG.log(Level.INFO, ReflectionToStringBuilder.toString(discovery, ToStringStyle.MULTI_LINE_STYLE));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to read or parse configuration", e);
        }

    }

}
