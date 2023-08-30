package test.jdkapi.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class TestInetAddress {
    public static void main( String[] args ) throws UnknownHostException, SocketException {
        testOne();
        System.out.println( "-----------------" );
        testAll();
    }

    static void testOne() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println( "Local HostAddress: " + addr.getHostAddress() );
        System.out.println( "Local host name: " + addr.getHostName() );
    }

    static void testAll() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while ( allNetInterfaces.hasMoreElements() ) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            // 去除回环接口，子接口，未运行和接口
            if ( netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp() ) {
                continue;
            }
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while ( addresses.hasMoreElements() ) {
                InetAddress ip = addresses.nextElement();
                if ( ip != null ) {
                    System.out.println( "ip = " + ip.getHostAddress() );
                }
            }
        }
    }

}
