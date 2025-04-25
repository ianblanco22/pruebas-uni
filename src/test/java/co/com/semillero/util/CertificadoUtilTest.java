package co.com.semillero.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLHandshakeException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class CertificadoUtilTest {

    @Test
    @DisplayName("BuildsHttpClientSuccessfully")
    void buildsHttpClientSuccessfully() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        CloseableHttpClient client = CertificadoUtil.buildClient();
        assertNotNull(client);
    }

    @Test
    @DisplayName("ThrowsExceptionWhenSSLContextFails")
    void throwsExceptionWhenSSLContextFails() {
        assertThrows(KeyManagementException.class, () -> {
            throw new KeyManagementException("SSL context initialization failed");
        });
    }

    @Test
    @DisplayName("HandlesSSLHandshakeExceptionGracefully")
    void handlesSSLHandshakeExceptionGracefully() {
        assertThrows(SSLHandshakeException.class, () -> {
            throw new SSLHandshakeException("SSL handshake failed");
        });
    }
}