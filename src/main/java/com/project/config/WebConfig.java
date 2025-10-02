package com.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.config.converter.StringToGuConverter;
import com.project.config.converter.StringToBeopjeongdongConverter;
import com.project.config.converter.StringToResidenceConverter;
import com.project.config.converter.StringToRiskConverter;
import com.project.config.converter.StringToSexConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${senior.photo.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/seniors/photos/**")
                .addResourceLocations("file:" + uploadPath);
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToRiskConverter());
        registry.addConverter(new StringToSexConverter());
        registry.addConverter(new StringToResidenceConverter());
        registry.addConverter(new StringToGuConverter());
        registry.addConverter(new StringToBeopjeongdongConverter());
    }
}