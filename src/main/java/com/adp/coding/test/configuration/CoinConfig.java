package com.adp.coding.test.configuration;

import com.adp.coding.test.model.CoinNumProperties;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinConfig {

    @Bean
    @ConfigurationProperties (prefix ="coin.num")
    public CoinNumProperties coinNumProperties() { return  new CoinNumProperties();}
}
