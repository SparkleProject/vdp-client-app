package com.example.vdp.connection.config;

import com.example.vdp.connection.controller.VdpHelloWorldController;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.security.KeyStore;

@Configuration
public class VdpClientAuthConfig {

    private static final Logger LOG = LoggerFactory.getLogger(VdpClientAuthConfig.class);

    @Value("classpath:dummy_key_file.jks")
    private Resource keyStoreData;

    @Value("${auth.keystore.password}")
    private String keyStorePassword;

    @Value("${auth.privatekey.password}")
    private String keyPassword;

    @Value("${auth.userid}")
    private String userId;

    @Value("${auth.password}")
    private String password;

    @Value("${endpoint.vdp.api.base}")
    private String vdpEndpoint;

    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder restTemplateBuilder
    ) {
        RestTemplate restTemplate = restTemplateBuilder
                .rootUri(vdpEndpoint)
                .basicAuthentication(userId, password)
                .build();

        try {
            LOG.info("Initialising two-way-ssl connection with vdp endpoint: {}", vdpEndpoint);

            // Make a KeyStore from the PKCS-12 file
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new BufferedInputStream(keyStoreData.getInputStream()), keyStorePassword.toCharArray());

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder()
                            .setTrustManagerFactoryAlgorithm("SunX509")
                            .setProtocol("TLS")
                            .setSecureRandom(null)
                            .loadKeyMaterial(keyStore, keyPassword.toCharArray()).build()
                    , NoopHostnameVerifier.INSTANCE);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(socketFactory)
                    .setMaxConnTotal(1)
                    .setMaxConnPerRoute(5)
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setReadTimeout(10000);
            requestFactory.setConnectionRequestTimeout(10000);
            restTemplate.setRequestFactory(requestFactory);

            LOG.info("Created RestTemplate with two-way-ssl auth.");
            return restTemplate;
        } catch (Exception exception) {
            LOG.error("Exception Occured while creating restTemplate:", exception);
        }
        return restTemplate;
    }
}
