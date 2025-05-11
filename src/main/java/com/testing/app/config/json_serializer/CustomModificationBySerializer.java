package com.testing.app.config.json_serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomModificationBySerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long modificationBy,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        if (modificationBy != null) {
            jsonGenerator.writeString("admin");  // Ghi tên người tạo
        } else {
            jsonGenerator.writeNull();  // Nếu không có id, ghi null
        }
    }
}
