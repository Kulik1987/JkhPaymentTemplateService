package ru.kulikovskiy.jkh.jkhpaymenttemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean(name = "daDataResttemplate")
    RestTemplate daDataServiceResttemplate() {
        return new RestTemplate();
    }
}
