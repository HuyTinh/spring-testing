package com.testing.app.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.app.controller.dto.request.CreateCustomerRequest;
import com.testing.app.shared.integrate.ControllerTest;
import com.testing.app.util.init_data_excel.GenericExcelValidationReader;
import com.testing.app.util.init_data_excel.ValueWithValidity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.isIn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Slf4j
@DisplayName("CustomerServiceTesting")
class CustomerControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Stream<Arguments> initData(String typeModification) throws Exception {
        String filePath = "src/test/resources/init_data_excel/data_test_validation_customer.xlsx";
        Map<String, List<ValueWithValidity>> testData =
                GenericExcelValidationReader.readValidationData(filePath, typeModification);

        List<Arguments> createdCustomerTestCases = new ArrayList<>();

        GenericExcelValidationReader.provideData(testData).forEach((combo -> {
            CreatedCustomerTestCase createdCustomerTestCase = new CreatedCustomerTestCase();

            CreateCustomerRequest customer = createdCustomerTestCase.getCreateCustomerRequest();
            combo.forEach((field, value) -> {
                if (combo.containsKey("name")) {
                    customer.setName(String.valueOf(combo.get("name").getValue()));
                }

                if (combo.containsKey("email")) {
                    customer.setEmail(String.valueOf(combo.get("email").getValue()));
                }

                if (combo.containsKey("address")) {
                    customer.setAddress(String.valueOf(combo.get("address").getValue()));
                }

                if (ObjectUtils.isNotEmpty(value.getMessage())) {
                    createdCustomerTestCase.getMessages().addAll(
                            Arrays.stream(value.getMessage().split(";")).collect(Collectors.toSet())
                                                                );
                }

                createdCustomerTestCase.getFieldValidates().add(value.isValid());
            });
            createdCustomerTestCases.add(Arguments.of(createdCustomerTestCase));
        }));

        return createdCustomerTestCases.stream();
    }

    private static Stream<Arguments> customerCreatedTestCases() throws Exception {
        return initData("create");
    }

    @Transactional
    @ParameterizedTest(name = "Test case {index}: {0}")
    @MethodSource("customerCreatedTestCases")
    @DisplayName("createCustomerController")
    void createCustomer(CreatedCustomerTestCase createdCustomerTestCase) {

        try {
            String content = objectMapper.writeValueAsString(createdCustomerTestCase.getCreateCustomerRequest());

            ResultMatcher resultMatcher = jsonPath("$.message", isIn(
                    createdCustomerTestCase.getMessages()
                                                                    ));

            mockMvc.perform(post("/api/v1/customers")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(content))
                   .andExpect(resultMatcher)
                   .andDo(super::logRequestAndResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}