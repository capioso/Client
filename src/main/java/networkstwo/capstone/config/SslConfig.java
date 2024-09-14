package networkstwo.capstone.config;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

public class SslConfig {
    private final String trustStorePath;
    private final String trustStorePassword;

    public SslConfig(String trustStorePath, String trustStorePassword) {
        this.trustStorePath = trustStorePath;
        this.trustStorePassword = trustStorePassword;
    }

    public SSLSocketFactory createSocketFactory() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (FileInputStream trustStoreInput = new FileInputStream(trustStorePath)) {
            trustStore.load(trustStoreInput, trustStorePassword.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        return sslContext.getSocketFactory();
    }
}
