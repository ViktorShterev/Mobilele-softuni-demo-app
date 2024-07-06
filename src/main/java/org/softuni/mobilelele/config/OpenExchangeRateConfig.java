package org.softuni.mobilelele.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "open.exchange.rates")
public class OpenExchangeRateConfig {

    private String host;

    private String appId;

    private String schema;

    private List<String> symbols;

    private String path;

    private boolean enabled;

    public String getPath() {
        return path;
    }

    public OpenExchangeRateConfig setPath(String path) {
        this.path = path;
        return this;
    }

    public String getHost() {
        return host;
    }

    public OpenExchangeRateConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public OpenExchangeRateConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public OpenExchangeRateConfig setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public OpenExchangeRateConfig setSymbols(List<String> symbols) {
        this.symbols = symbols;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public OpenExchangeRateConfig setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String toString() {
        return "OpenExchangeRateConfig{" +
                "host='" + host + '\'' +
                ", appId='" + appId + '\'' +
                ", schema='" + schema + '\'' +
                ", symbols=" + symbols +
                ", path='" + path + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
