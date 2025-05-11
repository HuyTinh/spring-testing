package com.testing.app.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.testing.app.common.enums.AbstractEnum;
import com.testing.app.config.json_serializer.CustomDateTimeSerializer;
import com.testing.app.config.json_serializer.CustomEnumSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {
    @Autowired
    private CustomDateTimeSerializer customDateTimeSerializer;

    @Autowired
    private CustomEnumSerializer customEnumSerializer;

    @Bean
    public SimpleModule customModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, customDateTimeSerializer);
        module.addSerializer(AbstractEnum.class, customEnumSerializer);
        return module;
    }
}
