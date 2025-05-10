package com.testing.app.domain.loyalty.dto.response;

import com.testing.app.common.response.BaseResponse;
import com.testing.app.domain.loyalty.LoyaltyTier;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseLoyaltyResponse extends BaseResponse<Long> {
    Integer pointBalance;

    LoyaltyTier tier;
}
