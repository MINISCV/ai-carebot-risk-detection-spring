package com.project.config.converter;

import com.project.domain.senior.Beopjeongdong;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBeopjeongdongConverter implements Converter<String, Beopjeongdong> {
    @Override
    public Beopjeongdong convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Beopjeongdong.from(source);
    }
}