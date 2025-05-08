package com.testing.app.domain.customer;

import com.testing.app.domain.customer.dto.response.BaseCustomerResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    /**
     * Mapper dữ liệu từ đối tượng khách hàng {@link Customer} sang Dto phản hồi {@link BaseCustomerResponse}.
     **/
    public BaseCustomerResponse toBaseResponse(Customer customer) {
        BaseCustomerResponse baseCustomerResponse = new BaseCustomerResponse();
        BeanUtils.copyProperties(customer, baseCustomerResponse);

        return baseCustomerResponse;
    }
}
