package br.com.software.springbootapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    @ConfigurationProperties("projeto.configuracao-personalizada")
    public ConfiguracaoPersonlizada getConfiguracaoPersonlizada() {
        return new ConfiguracaoPersonlizada();
    }

}
