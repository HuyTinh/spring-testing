package com.testing.app.shared.integrate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.app.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@AutoConfigureMockMvc
public abstract class ControllerTest extends BaseIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CommonUtils commonUtils;


    protected void logRequestAndResponse(MvcResult result) throws UnsupportedEncodingException {
        String requestBody = result.getRequest().getContentAsString();
        String responseBody = result.getResponse().getContentAsString();

        try {
            String formattedRequest = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    objectMapper.readTree(requestBody));
            String formattedResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    objectMapper.readTree(responseBody));

            log.info("\n[REQUEST]:\n{}\n[RESPONSE]:\n{}\n[STATUS]: {}\n[TIMESTAMP]: {}",
                     formattedRequest,
                     formattedResponse,
                     result.getResponse().getStatus(),
                     LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    );
        } catch (Exception e) {
            log.error("Error logging request/response", e);
        }
    }
}
