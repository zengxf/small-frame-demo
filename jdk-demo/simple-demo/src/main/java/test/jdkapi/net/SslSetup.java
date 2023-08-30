package test.jdkapi.net;

import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SslSetup {

    /*** 允许不安全的 HTTPS 连接 */
    public static HttpClient httpClient() throws Exception {
        TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted( X509Certificate[] arg0, String arg1 ) throws CertificateException {
            }

            @Override
            public void checkServerTrusted( X509Certificate[] arg0, String arg1 ) throws CertificateException {
            }
        } };

        System.setProperty( "jdk.internal.httpclient.disableHostnameVerification", "true" ); // 取消主机名验证

        SSLParameters params = new SSLParameters();
        params.setEndpointIdentificationAlgorithm( "" );
        SSLContext sc = SSLContext.getInstance( "SSL" );
        sc.init( null, trustAllCertificates, new SecureRandom() );

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout( Duration.ofSeconds( 60 ) )
                .followRedirects( HttpClient.Redirect.ALWAYS )
                .sslContext( sc )
                .sslParameters( params )
                .build();

        return client;
    }

    /*** JDK11 HttpClient 没用 */
    public static void notVerfiy() {
        try {
            trustAllHttpsCertificates();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultHostnameVerifier( HV );
    }

    static HostnameVerifier HV = new HostnameVerifier() {
        public boolean verify( String urlHostName, SSLSession session ) {
            System.out.println( "Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost() );
            return true;
        }
    };

    static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new MyTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance( "SSL" );
        sc.init( null, trustAllCerts, null );
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );
    }

    static class MyTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted( java.security.cert.X509Certificate[] certs ) {
            return true;
        }

        public boolean isClientTrusted( java.security.cert.X509Certificate[] certs ) {
            return true;
        }

        public void checkServerTrusted( java.security.cert.X509Certificate[] certs, String authType ) throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted( java.security.cert.X509Certificate[] certs, String authType ) throws java.security.cert.CertificateException {
            return;
        }
    }

}
