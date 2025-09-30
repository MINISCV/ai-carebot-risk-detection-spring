package com.project.config.converter;

import com.project.domain.senior.Haengjeongdong;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHaengjeongdongConverter implements Converter<String, Haengjeongdong> {
    @Override
    public Haengjeongdong convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Haengjeongdong.from(source);
    }
}