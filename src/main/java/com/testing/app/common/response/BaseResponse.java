package com.testing.app.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BaseResponse<T> extends ExtraFieldResponse {
    T id;

    LocalDateTime createdAt;

    @JsonProperty("createdName")
    String createdBy;

    LocalDateTime updatedAt;

    @JsonProperty("updatedName")
    String updatedBy;
}
