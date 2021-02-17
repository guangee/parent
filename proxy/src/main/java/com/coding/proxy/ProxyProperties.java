package com.coding.proxy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/guangee/
 */
@Data
@ConfigurationProperties("proxy")
public class ProxyProperties {
    private String clientKey;
    private String serverHost;
    private Integer serverPort = 4900;
    private Boolean sslEnable;
    private String sslJksPath;
    private String sslKeyStorePassword;
}
