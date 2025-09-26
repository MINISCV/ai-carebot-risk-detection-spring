package com.project.config.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.project.util.StringCaseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class SnakeCaseToCamelCaseRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String[]> convertedParams;

    public SnakeCaseToCamelCaseRequestWrapper(HttpServletRequest request) {
        super(request);
        this.convertedParams = convertParameterMap(request.getParameterMap());
    }

    @Override
    public String getParameter(String name) {
        String camelName = StringCaseUtil.snakeToCamel(name);
        String[] values = convertedParams.get(camelName);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(convertedParams);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(convertedParams.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String camelName = StringCaseUtil.snakeToCamel(name);
        return convertedParams.get(camelName);
    }

    private Map<String, String[]> convertParameterMap(Map<String, String[]> originalMap) {
        Map<String, String[]> newMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : originalMap.entrySet()) {
            String camelKey = StringCaseUtil.snakeToCamel(entry.getKey());
            newMap.put(camelKey, entry.getValue());
        }
        return newMap;
    }
}