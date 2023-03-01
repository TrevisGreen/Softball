package com.sofball.demo.config;


import com.sofball.demo.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;


@Configuration
@Profile(Constants.SPRING_PROFILE_PRODUCTION)
public class ThymeleafConfig {

    private static final Logger log = LoggerFactory.getLogger(ThymeleafConfig.class);

    @Value("${spring.thymeleaf.cache}")
    private Boolean cache;

    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        log.info("Setting Thymeleaf Resolver Cache to {}", cache);
        TemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(cache);
        return resolver;
    }

}
