package com.snd.storefinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ElasticConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;
    @Value("${spring.elasticsearch.api-key}")
    private String apiKey;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Authorization", "ApiKey " + apiKey);
                    return headers;
                })
                .build();
    }
}
