package com.testing.app.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.testing.app.config.json_serializer.CustomModificationBySerializer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class BaseResponse<T> extends ExtraFieldResponse {
    T id;

    LocalDateTime createdAt;

    @JsonProperty("createdName")
    @JsonSerialize(using = CustomModificationBySerializer.class)
    Long createdBy;

    LocalDateTime updatedAt;

    @JsonProperty("updatedName")
    @JsonSerialize(using = CustomModificationBySerializer.class)
    Long updatedBy;
}
