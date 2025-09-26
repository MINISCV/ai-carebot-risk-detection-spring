package com.project.config.converter;

import com.project.domain.senior.Sex;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSexConverter implements Converter<String, Sex> {
    @Override
    public Sex convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Sex.from(source);
    }
}