package com.testing.app.config.json_serializer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.testing.app.common.enums.AbstractEnum;

import java.time.LocalDateTime;

public class CustomModule extends SimpleModule {
    public CustomModule() {
        this
                .addSerializer(LocalDateTime.class, new CustomDateTimeSerializer())
                .addSerializer(AbstractEnum.class, new CustomEnumSerializer());

    }
}
