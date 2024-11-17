package com.example.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class AddressUtils {

    public static String getAddress(){
        String address = "258";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
            byte[] macAddressBytes = networkInterface.getHardwareAddress();

            StringBuilder macAddress = new StringBuilder();
            for (int i = 0; i < macAddressBytes.length; i++) {
                macAddress.append(String.format("%02X", macAddressBytes[i]));
                if (i < macAddressBytes.length - 1) {
                    macAddress.append("");
                }
            }
            address = macAddress.toString();
        } catch (Exception ex){
        }
        return address;
    }
}
