package ru.clevertec.ecl.integration.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class OrderControllerTest extends BaseIntegrationTest {

    private static final Long USER_ID = 1L;

    private static final Long CERTIFICATE_ID = 5L;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllByUserIdShouldReturnCorrectCountOfOrdersAndOkStatus() {
        int expectedSize = 3;

        mockMvc.perform(get("/api/v0/users/" + USER_ID + "/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(expectedSize));
    }

    @Test
    @SneakyThrows
    void makeOrderShouldCreateAndReturnOrderWithCreatedStatus() {
        mockMvc.perform(post("/api/v0/users/" + USER_ID + "/orders?certificateId=" + CERTIFICATE_ID))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPrice").isNotEmpty())
                .andExpect(jsonPath("$.purchaseTime").isNotEmpty());
    }
}