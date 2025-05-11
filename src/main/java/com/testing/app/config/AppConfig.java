package com.testing.app.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper(SimpleModule customModule) {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(customModule)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // Định dạng datetime ISO-8601
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Bỏ qua field không có trong class
                .enable(SerializationFeature.FAIL_ON_SELF_REFERENCES) // Bật chế độ kiểm tra self-references
                .enable(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL) // Để các đối tượng tự tham chiếu thành null khi serializing
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                                                      .configure()
                                                      .failFast(true)
                                                      .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
