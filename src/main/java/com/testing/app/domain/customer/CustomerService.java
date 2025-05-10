package com.testing.app.domain.customer;

import com.testing.app.domain.customer.dto.request.CreateCustomerRequest;
import com.testing.app.domain.customer.dto.request.UpdateCustomerRequest;
import com.testing.app.domain.customer.dto.response.BaseCustomerResponse;
import com.testing.app.domain.loyalty.Loyalty;
import com.testing.app.domain.loyalty.LoyaltyRepository;
import com.testing.app.exception.CustomerEmailUnavailableException;
import com.testing.app.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final LoyaltyRepository loyaltyRepository;

    private final CustomerMapper customerMapper;

    /**
     * Lấy danh sách khách hàng.
     *
     * @return danh sách khách hàng {@code List<Customer>}.
     * @author HuyTinh
     **/
    public List<BaseCustomerResponse> getCustomers() {
        log.info("Lấy danh sách khách hàng.");

        // Lấy danh sách khách hàng.
        return customerRepository.findAll().stream().map(customerMapper::toBaseResponse).toList();
    }

    /**
     * Tìm khách hàng với Id.
     *
     * @param id mã id khách hàng cần tìm kiếm.
     * @return khách hàng {@code Customer}.
     * @author HuyTinh
     **/
    public BaseCustomerResponse getCustomerById(Long id) {
        log.info("Tìm kiếm khách hàng với ID [{}] trong cơ sở dữ liệu.", id);

        // Tìm kiếm khách hàng với id tương ứng trong cơ sở dữ liệu.
        return customerMapper.toBaseResponse(
                customerRepository.findById(id).orElseThrow(
                        () -> new CustomerNotFoundException(
                                String.format("Không tìm thấy khách hàng với ID [%d].", id)
                        )
                                                           )
                                            );
    }

    /**
     * Tạo khách hàng.
     *
     * @param request {@link CreateCustomerRequest} yêu cầu (request) tạo khách hàng.
     * @author HuyTinh
     **/
    public BaseCustomerResponse createCustomer(CreateCustomerRequest request) {
        // Kiểm tra dữ liệu yêu cầu tạo mới khách hàng.
        validateCreateCustomerRequest(request);

        // Tạo đối tượng khách hàng.
        Customer createCustomer = processCreateCustomerRequest(request);

        // Lưu khách hàng vào cơ sở dữ liệu.
        return customerMapper.toBaseResponse(customerRepository.save(createCustomer));
    }

    /**
     * Cập nhật khách hàng.
     *
     * @param request {@link UpdateCustomerRequest} yêu cầu (request) cập nhật khách hàng.
     * @author HuyTinh
     **/
    public void updateCustomer(UpdateCustomerRequest request) {
    }

    /**
     * Kiểm tra dữ liệu yêu cầu (request) tạo khách hàng.
     *
     * @param request là yêu cầu (request) tạo khách hàng {@link CreateCustomerRequest} từ người dùng (client).
     * @author HuyTinh
     **/
    private void validateCreateCustomerRequest(CreateCustomerRequest request) {
        // Tìm kiếm khách hàng với email tương ứng trong cơ sở dữ liệu.
        Optional<Customer> customerByEmail = customerRepository.findByEmail(request.getEmail());

        // Nếu đã có khách hàng với email tương ứng.
        if (customerByEmail.isPresent()) {
            // Ném ngoại lệ "Email [%s] không hợp lệ.".
            throw new CustomerEmailUnavailableException(
                    String.format("Email [%s] không hợp lệ.", request.getEmail())
            );
        }
    }

    /**
     * Xử lý dữ liệu tạo mới khách hàng trước khi lưu vào cơ sở dữ liệu.
     *
     * @param request là yêu cầu (request) tạo mới khách hàng {@link CreateCustomerRequest} từ người dùng (client).
     * @return khách hàng đã xử lý {@code Customer}.
     * @author HuyTinh
     **/
    private Customer processCreateCustomerRequest(CreateCustomerRequest request) {
        // Tạo đối tượng khách hàng.
        Customer customer = Customer.builder()
                                    .name(request.getName())
                                    .email(request.getEmail())
                                    .address(request.getAddress())
                                    .build();

        // Thêm thứ hạng khách hàng.
        request.getLoyalties().forEach(createCustomerLoyaltyRequest -> {
            // Tìm kiếm trong cơ sở dữ liệu.
            Optional<Loyalty> loyaltyOpt = loyaltyRepository.findById(createCustomerLoyaltyRequest.getId());

            // Nếu tìm thấy thì thêm vào Set<loyalty> khách hàng.
            loyaltyOpt.ifPresent(loyalty -> customer.getLoyalties().add(loyalty));

            // Nếu không tìm thấy thì ném ngoại lệ "Không tìm thấy thứ hạng mã Id [%s].".
            loyaltyOpt.orElseThrow(() -> new RuntimeException(
                    String.format("Không tìm thấy thứ hạng mã Id [%s].", createCustomerLoyaltyRequest.getId())
            ));
        });


        return customer;
    }
}
