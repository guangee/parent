package com.coding.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/zziaguan/
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "guanweiming.weblog", name = "enabled")
public class WebLogAutoConfigure {


    @Bean
    @ConditionalOnMissingBean
    public WebLog webLog() {
        log.info("配置日志拦截器");
        return new WebLog();
    }
}
