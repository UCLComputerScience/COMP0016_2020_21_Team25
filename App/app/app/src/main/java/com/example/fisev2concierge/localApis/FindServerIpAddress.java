package com.example.fisev2concierge.localApis;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class FindServerIpAddress implements Runnable{

    private volatile String ip;
    private volatile boolean ready = false;

    public synchronized String getIp() {
        while(!ready){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    private void findIp(){
        try {
            sendPakcet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPakcet() throws IOException{
        DatagramSocket port = new DatagramSocket();
        port.setBroadcast(true);
        byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8100);
        port.send(sendPacket);
        broadcastMessage(sendData, port);
    }

    private void broadcastMessage(byte[] sendData, DatagramSocket port) throws IOException{
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
                // Don't want to broadcast to the loopback interface
            }
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress inetAddress = interfaceAddress.getBroadcast();
                if (inetAddress == null) {
                    continue;
                }
                // Send the broadcast package!
                DatagramPacket sendNewPacket = new DatagramPacket(sendData, sendData.length, inetAddress, 8100);
                port.send(sendNewPacket);
            }
        }
        handleResponse(port);
    }

    private void handleResponse(DatagramSocket port) throws IOException{
        byte[] responseByteArray = new byte[15000];
        DatagramPacket receivePacket = new DatagramPacket(responseByteArray, responseByteArray.length);
        port.receive(receivePacket);
        //We have a response, check if the message is correct
        String message = new String(receivePacket.getData()).trim();
        if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
            ip = receivePacket.getAddress().toString();
        }
        port.close();
        ready = true;
    }


    @Override
    public synchronized void run() {
        findIp();
        notifyAll();
    }
}
