package com.ddib.user.user.setting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    private final String releaseHostName ;

    public CorsMvcConfig(@Value("${releaseHostName}") String releaseHostName) {
        this.releaseHostName = releaseHostName;
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .exposedHeaders("Authorization")
                .allowedOrigins("http://"+ releaseHostName+":3000",
                        "https://"+ releaseHostName, "https://" + releaseHostName + ":3000")
                .allowCredentials(true);
    }
}
