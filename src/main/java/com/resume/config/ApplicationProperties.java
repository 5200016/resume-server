package com.resume.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String jwtprefix;

    public ApplicationProperties() {
        super();
    }

    public String getJwtprefix() {
        return jwtprefix;
    }

    public void setJwtprefix(String jwtprefix) {
        this.jwtprefix = jwtprefix;
    }
}
