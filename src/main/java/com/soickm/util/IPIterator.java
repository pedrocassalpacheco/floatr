/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soickm.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro.pacheco
 */
public class IPIterator implements Iterator<InetAddress> {

    private final InetAddress start, end;
    private InetAddress current;

    public IPIterator(InetAddress start, InetAddress end) {
        this.start = start;
        this.current = start;
        this.end = end;
    }

    @Override
    public boolean hasNext() {
        return !current.equals(end);
    }

    @Override
    public InetAddress next() {
        try {
            current = this.getNextIPAddress(current);
        } catch (UnknownHostException ex) {
            Logger.getLogger(IPIterator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return current;
    }

    private InetAddress getNextIPAddress(InetAddress ip) throws UnknownHostException {
        return InetAddress.getByName(getNextIPAddress(ip.getHostAddress()));
    }

    private String getNextIPAddressAsString(InetAddress ip) {
        return getNextIPAddress(ip.getHostAddress());
    }

    private String getNextIPAddress(String ip) {
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
