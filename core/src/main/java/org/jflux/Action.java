package org.jflux;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Action must be immutable
public class Action {

    private final Map<String, Object> properties;

    Action(Map<String, Object> properties) {
        this.properties = Collections.unmodifiableMap(properties);
    }

    public <T> T get(String key) {
        return (T) properties.get(key);
    }

    public static class Builder {
        private Map<String, Object> properties = new HashMap<>();
        public <T> Builder set(String key, T value) {
            properties.put(key, value);
            return this;
        }
        public Action build() {
            return new Action(properties);
        }
    }
}
