package com.testing.app.customer;

import com.testing.app.customer.dto.request.CreateCustomerRequest;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatedCustomerTestCase {
    @Builder.Default
    private CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();

    @Builder.Default
    @ToString.Exclude
    private List<Boolean> fieldValidates = new ArrayList<>();

    private Integer expected;

    @Builder.Default
    private Set<String> messages = new HashSet<>();

    public Integer getExpected() {
        boolean pass = fieldValidates.stream().reduce(true, (a, b) -> a && b);
        return pass ? 200 : 400;
    }
}