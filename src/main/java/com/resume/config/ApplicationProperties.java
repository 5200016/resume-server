package com.resume.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String jwtprefix;

    public ApplicationProperties() {
    }

    public String getJwtprefix() {
        return jwtprefix;
    }

    public void setJwtprefix(String jwtprefix) {
        this.jwtprefix = jwtprefix;
    }
}