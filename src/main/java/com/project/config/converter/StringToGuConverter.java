package com.project.config.converter;

import com.project.domain.senior.Gu;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGuConverter implements Converter<String, Gu> {
    @Override
    public Gu convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Gu.from(source);
    }
}