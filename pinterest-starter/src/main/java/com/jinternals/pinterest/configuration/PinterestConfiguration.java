package com.jinternals.pinterest.configuration;


import com.chrisdempewolf.pinterest.Pinterest;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Pinterest.class)
@ConditionalOnProperty(prefix = "pinterest" , name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(PinterestProperties.class)
public class PinterestConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Pinterest pinterest(PinterestProperties properties){
        return new Pinterest(properties.getToken());
    }

}
