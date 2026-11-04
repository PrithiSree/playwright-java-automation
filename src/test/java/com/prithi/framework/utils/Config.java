package com.prithi.framework.utils;

public final class Config {

    private Config() {}

    public static final String BASE_URL = System.getProperty("baseUrl", "https://www.saucedemo.com");
    public static final String API_BASE_URL = System.getProperty("apiBaseUrl", "https://jsonplaceholder.typicode.com");

    public static boolean headless() {
        return Boolean.parseBoolean(valueOrDefault("headless", "true"));
    }

    public static String browser() {
        return valueOrDefault("browser", "chromium").toLowerCase();
    }

    private static String valueOrDefault(String key, String fallback) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank() || value.startsWith("${")) {
            return fallback;
        }
        return value;
    }
}
