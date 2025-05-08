package com.testing.app.common.enums;

public interface AbstractEnum<T> {
    T getValue();
    String getName(); // hoặc dùng `name()` nếu muốn lấy tên enum
}
