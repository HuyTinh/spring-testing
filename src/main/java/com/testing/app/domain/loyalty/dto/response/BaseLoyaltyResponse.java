package com.testing.app.domain.loyalty.dto.response;

import com.testing.app.common.response.BaseResponse;
import com.testing.app.domain.loyalty.LoyaltyTier;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseLoyaltyResponse extends BaseResponse<Long> {
    private Integer pointBalance;

    private LoyaltyTier tier;
}
