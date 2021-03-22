package backend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Broadcast server IP address over the local network using UDP
 */
public class DiscoveryThread implements Runnable {
    private boolean running = false;
    DatagramSocket socket;

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        running = true;
        try {
            //Keep a socket open to listen to all the UDP traffic that is destined for this port
            socket = new DatagramSocket(Integer.parseInt(App.PORT), InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (isRunning()) {
                ApiLogger.log("Backend ready to receive broadcast packets @ " + InetAddress.getLocalHost().getHostAddress());
                System.out.println();

                //Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                //Packet received
                ApiLogger.log("Discovery packet received from: " + packet.getAddress().getHostAddress());
                ApiLogger.log("Packet received; data: " + new String(packet.getData()));

                //See if the packet holds the right command (message)
                String message = new String(packet.getData()).trim();
                if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
                    byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

                    //Send a response
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                    socket.send(sendPacket);

                    ApiLogger.log("Sent packet to: " + sendPacket.getAddress().getHostAddress());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DiscoveryThread getInstance() {
        return DiscoveryThreadHolder.INSTANCE;
    }

    private static class DiscoveryThreadHolder {
        private static final DiscoveryThread INSTANCE = new DiscoveryThread();
    }

}