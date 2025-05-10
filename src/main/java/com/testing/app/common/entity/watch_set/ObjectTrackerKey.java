package com.testing.app.common.entity.watch_set;

import com.testing.app.common.entity.AbstractEntity;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectTrackerKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Map<Class<?>, Field> ID_FIELD_CACHE = new ConcurrentHashMap<>();

    private final Class<?> type;
    private final Object key;

    public ObjectTrackerKey(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }

        this.type = obj.getClass();
        this.key = resolveKey(obj);
    }

    private Object resolveKey(Object obj) {
        if (obj instanceof AbstractEntity entity) {
            return entity.getId();
        }

        Field idField =
                ID_FIELD_CACHE.computeIfAbsent(
                        obj.getClass(),
                        cls -> {
                            try {
                                Field field = cls.getDeclaredField("id");
                                field.setAccessible(true);
                                return field;
                            } catch (NoSuchFieldException e) {
                                return null;
                            }
                        });

        if (idField != null) {
            try {
                return idField.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access 'id' field in " + obj.getClass(), e);
            }
        }

        // Fallback cuối cùng nếu không có field id
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectTrackerKey other)) return false;
        return type.equals(other.type) && Objects.equals(key, other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key);
    }

    @Override
    public String toString() {
        return type.getSimpleName() + "#" + key;
    }
}
