package com.spring.pub_sub_app.config;

import org.apache.pulsar.client.api.AuthenticationFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.PulsarClientBuilderCustomizer;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Configuration
public class PulsarConfig {

    @Value("${pulsar.service-url}")
    String pulsarServiceUrl;

    @Value("${pulsar.token}")
    String pulsarToken;

    @Bean
    PulsarClientBuilderCustomizer pulsarClientPemSslCustomizer() {
        return (clientBuilder) -> {
            clientBuilder.serviceUrl(pulsarServiceUrl);
            clientBuilder.tlsTrustCertsFilePath(this.resolvePath("classpath:ca.cert.pem"));
            clientBuilder.authentication(AuthenticationFactory.token(pulsarToken));
            clientBuilder.enableTlsHostnameVerification(false);
            clientBuilder.allowTlsInsecureConnection(false);
        };
    }

    private String resolvePath(String resourceLocation) {
        try {
            return ResourceUtils.getURL(resourceLocation).getPath();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
