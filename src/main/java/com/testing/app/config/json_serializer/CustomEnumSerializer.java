package com.testing.app.config.json_serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.testing.app.common.enums.AbstractEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomEnumSerializer extends JsonSerializer<AbstractEnum> {
    @Override
    public void serialize(AbstractEnum absEnum,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("name", absEnum.getName());
        result.put("value", absEnum.getValue());

        jsonGenerator.writeObject(result);
    }
}
