package com.testing.app.common.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExtraFieldResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter(AccessLevel.NONE)
    Map<String, Object> extraFields;

    @JsonAnyGetter // Trả về tất cả các field trong extraFields
    public Map<String, Object> getExtraFields() {
        return extraFields;
    }
}
