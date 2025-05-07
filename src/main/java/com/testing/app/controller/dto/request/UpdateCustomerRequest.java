package com.testing.app.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCustomerRequest extends BaseCustomerRequest {

    @NotNull(message = "Không được để trống mã Id hợp đồng.")
    private Long id;
}
