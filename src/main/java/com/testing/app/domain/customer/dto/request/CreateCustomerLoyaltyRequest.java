package com.testing.app.domain.customer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
public class CreateCustomerLoyaltyRequest {

    @NotNull(message = "Mã Id không thể để trống.")
    private Long id;

    @NotNull(message = "Điểm thưởng không thể để trống.")
    @Min(value = 0, message = "Điểm thưởng không thể bé hơn 0.")
    private Integer pointBalance;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
