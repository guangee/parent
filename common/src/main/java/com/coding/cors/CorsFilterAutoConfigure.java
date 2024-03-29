package com.coding.cors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@EnableConfigurationProperties(CorsProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.cors", name = "enabled")
public class CorsFilterAutoConfigure {

    private final CorsProperties corsProperties;

    @Autowired
    public CorsFilterAutoConfigure(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }


//    @Bean
//    @ConditionalOnMissingBean
//    public CorsFilter corsFilter() {
//        log.debug("配置跨域");
//        return new CorsFilter(corsProperties.getMaxAge(), corsProperties.getAllowOrigin(), corsProperties.getAllowMethods());
//    }
}
