/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class IP {

    private static final Logger LOG = Logger.getLogger(IP.class.getName());

    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

    public static InetAddress getNextIPAddress(InetAddress ip) throws UnknownHostException {        
        return InetAddress.getByName(getNextIPAddress(ip.getHostAddress()));
    }
    
    public static String getNextIPAddressAsString(InetAddress ip) {        
        return getNextIPAddress(ip.getHostAddress());
    }
    
    public static String getNextIPAddress(String ip) {
        System.out.println(ip);
        String[] nums = ip.split("\\.");
        int i = (Integer.parseInt(nums[0]) << 24 | Integer.parseInt(nums[2]) << 8
                | Integer.parseInt(nums[1]) << 16 | Integer.parseInt(nums[3])) + 1;

        // If you wish to skip over .255 addresses.
        if ((byte) i == -1) {
            i++;
        }

        return String.format("%d.%d.%d.%d", i >>> 24 & 0xFF, i >> 16 & 0xFF,
                i >> 8 & 0xFF, i >> 0 & 0xFF);
    }
}
