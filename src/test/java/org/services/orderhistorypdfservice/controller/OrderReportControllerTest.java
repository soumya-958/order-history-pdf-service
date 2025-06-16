package org.services.orderhistorypdfservice.controller;

import org.services.orderhistorypdfservice.dto.OrderRequestDto;
import org.services.orderhistorypdfservice.service.interfaces.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderReportController.class)
public class OrderReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void generateReport_validInput_returnsPdf() throws Exception {
        byte[] pdfBytes = "test-pdf".getBytes();

        Mockito.when(orderService.generateOrderReport(any(OrderRequestDto.class))).thenReturn(pdfBytes);

        mockMvc.perform(post("/api/orders/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "customerId": "CUST001",
                      "fromDate": "2023-01-01",
                      "toDate": "2023-12-31"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(content().bytes(pdfBytes));
    }

    @Test
    void testValidationError_missingCustomerId_returnsBadRequest() throws Exception {
        String invalidJson = """
            {
              "customerId": "",
              "fromDate": "2023-01-01",
              "toDate": "2023-12-31"
            }
        """;

        mockMvc.perform(post("/api/orders/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
