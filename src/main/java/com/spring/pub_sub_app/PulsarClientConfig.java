package com.spring.pub_sub_app;

import java.io.FileNotFoundException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.core.PulsarClientBuilderCustomizer;
import org.springframework.util.ResourceUtils;

@Configuration
public class PulsarClientConfig {

        @Bean
        PulsarClientBuilderCustomizer pulsarClientPemSslCustomizer() {
            return (clientBuilder) -> {
                clientBuilder
                        .tlsTrustCertsFilePath(this
                                .resolvePath("classpath:ca.cert.pem"));
            };
        }

        private String resolvePath(String resourceLocation) {
            try {
                return ResourceUtils.getURL(resourceLocation).getPath();
            }
            catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
