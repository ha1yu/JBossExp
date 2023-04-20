package org.ha1yu;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;

public class SSLContextFactory {
    public SSLContextFactory() {
    }

    public static SSLContext getSSLContext() {
        SSLContext sslContext = null;

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            sslContext = SSLContexts.custom().useSSL().loadTrustMaterial(trustStore, anyTrustStrategy).build();
        } catch (KeyStoreException var3) {
        } catch (NoSuchAlgorithmException var4) {
        } catch (KeyManagementException var5) {
        }

        return sslContext;
    }
}