package com.example.filmlibrary.source.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafHotSwap {

    private final ThymeleafProperties thymeleafProperties;

    public ThymeleafHotSwap(ThymeleafProperties thymeleafProperties) {
        this.thymeleafProperties = thymeleafProperties;
    }

    @Value("${spring.thymeleaf.templates_root}")
    private String templateRoot;

    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setSuffix(thymeleafProperties.getSuffix());
        resolver.setPrefix(templateRoot);
        resolver.setTemplateMode(thymeleafProperties.getMode());
        resolver.setCacheable(thymeleafProperties.isCache());

        return resolver;
    }
}
