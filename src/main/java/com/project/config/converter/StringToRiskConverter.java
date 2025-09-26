package com.project.config.converter;

import com.project.domain.analysis.Risk;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRiskConverter implements Converter<String, Risk> {

    @Override
    public Risk convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Risk.from(source);
    }
}