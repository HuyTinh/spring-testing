package com.testing.app.customer;

import com.testing.app.customer.dto.response.BaseCustomerResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public BaseCustomerResponse toBaseResponse(Customer customer){
        BaseCustomerResponse baseCustomerResponse = new BaseCustomerResponse();
        BeanUtils.copyProperties(customer, baseCustomerResponse);

        return baseCustomerResponse;
    }
}
