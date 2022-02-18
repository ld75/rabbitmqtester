package org.rabbitmqclient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeoutException;

public class RabbitMqClient {
    public void sendmessage(String hostname, int port, String QUEUE_NAME, String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(port);
        SSLContext sslcontext=null;
        try {
             sslcontext = prepareSSLProtocol();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        useSSLProtocol(sslcontext,hostname, factory);
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel())
        {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private SSLContext prepareSSLProtocol() throws Exception {
        try {
            TrustManager[] tms = prepareTrustManager();
            KeyManagerFactory kmf = prepareKeyManager();
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            //sslContext.init(kmf.getKeyManagers(), tms, null);
            sslContext.init(null, tms, null);
            return sslContext;
                }
               /* catch (IOException e) {
                    e.printStackTrace();
                }*/
                catch (NoSuchAlgorithmException | KeyManagementException  e) {
                    e.printStackTrace();
                }
                /*catch (CertificateException | KeyStoreException | UnrecoverableKeyException e) {
                    e.printStackTrace();
                }*/
        throw new Exception("pas pu init ssl");
    }

    private KeyManagerFactory prepareKeyManager() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        char[] keyPassphrase = "testrabbit".toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream("./testrabbit.p12"), keyPassphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassphrase);
        return kmf;
    }

    private TrustManager[] prepareTrustManager() {
        // fake TLS
        TrustManager[] tms = new TrustManager[0];
        tms = new TrustManager[]
                {
                        new X509TrustManager()
                        {
                            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; } // ou retur null;?
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {}  // ca c pour ignorer la securite trust.
                        }
                };
        return tms;

/*
    veritable TLS
        char[] trustPassphrase = "rabbitstore".toCharArray();
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream("./trustStore"), trustPassphrase);

        TrustManagerFactory tms = TrustManagerFactory.getInstance("SunX509");
        try {
            tms.init(tks);

            return tms.getTrustManagers();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }*/
    }

    private void useSSLProtocol(SSLContext sslcontext, String hostname, ConnectionFactory factory) {
        factory.setHost(hostname);
            factory.useSslProtocol(sslcontext);
    }

    public class TrustAllHostNameVerifier implements HostnameVerifier {   //uniquement je crois pour HttpsURLConnection
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
}
}
