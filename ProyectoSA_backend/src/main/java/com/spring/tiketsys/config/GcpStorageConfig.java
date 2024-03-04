package com.spring.tiketsys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;

@Configuration
public class GcpStorageConfig {

    @Value("${spring.cloud.gcp.storage.bucket-location}")
    private String bucketLocation;

    @Bean
    public Resource bucketLocation() throws IOException {
        if (StringUtils.isEmpty(bucketLocation)) {
            throw new IllegalArgumentException("Bucket location must not be empty");
        }
        // You can use the bucketLocation value as needed
        return new UrlResource("https://storage.googleapis.com/" + bucketLocation);
    }
}
