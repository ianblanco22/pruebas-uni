/**
 * Clase util encargada de crear certificado SSL
 */
package co.com.semillero.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * CertificadoUtil
 * <p>
 * Desarrollo ATH - Modelo Agregador ISO 20022
 * <p>
 * Creado él: 18 de octubre de 2024
 * <p>
 * Autor: Laura Valentina Castaño Torres
 * <p>
 * Autor: Laura Valentina Castaño Torres
 * <p>
 * Requerimiento: Desarrollo de Modelo Agregador ISO 20022
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 * <p>
 * Clase encargada de conectarse con el parameter Store
 *
 * @author Laura Valentina Castaño Torres
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class CertificadoUtil {

    public static CloseableHttpClient buildClient()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // Create SSL context that accepts all certificates
        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial((chain, authType) -> true).build();

        // Create custom trust manager that accepts all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        // Set the custom trust manager in the SSL context
        sslContext.init(null, trustAllCerts, null);

        // Create SSL socket factory with the custom SSL context
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);


        // Create HttpClient with the custom SSL socket factory
        return HttpClients.custom().setSSLSocketFactory(sslSocketFactory).
                build();
    }


}