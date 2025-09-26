package com.project.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.project.domain.senior.Residence;

@Component
public class StringToResidenceConverter implements Converter<String, Residence> {
    @Override
    public Residence convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Residence.from(source);
    }
}