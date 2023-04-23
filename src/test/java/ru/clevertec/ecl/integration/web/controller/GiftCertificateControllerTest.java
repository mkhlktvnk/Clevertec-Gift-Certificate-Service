package ru.clevertec.ecl.integration.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.web.model.GiftCertificateModel;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class GiftCertificateControllerTest extends BaseIntegrationTest {

    private static final Long CERTIFICATE_ID = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllByPageableAndCriteriaShouldReturnCorrectCountOfCertificatesAndOkStatus() {
        int expectedSize = 3;
        mockMvc.perform(get("/api/v0/certificates?page=0&size=" + expectedSize))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(expectedSize));
    }

    @Test
    @SneakyThrows
    void findByIdShouldReturnCorrectCertificateAndOkStatus() {
        mockMvc.perform(get("/api/v0/certificates/" + CERTIFICATE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(CERTIFICATE_ID));
    }

    @Test
    @SneakyThrows
    void saveShouldReturnSavedCertificateAndCreatedStatus() {
        GiftCertificateModel expectedModel = GiftCertificateModel.builder()
                .name("Spa Package")
                .description("Relax and rejuvenate with our spa package.")
                .price(BigDecimal.valueOf(150))
                .duration(90)
                .build();

        String requestBody = objectMapper.writeValueAsString(expectedModel);

        mockMvc.perform(post("/api/v0/certificates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(expectedModel.getName()))
                .andExpect(jsonPath("$.description").value(expectedModel.getDescription()))
                .andExpect(jsonPath("$.price").value(expectedModel.getPrice()))
                .andExpect(jsonPath("$.duration").value(expectedModel.getDuration()));
    }

    @Test
    @SneakyThrows
    void updateByIdShouldReturnNoContentStatus() {
        GiftCertificateModel updateCertificate = GiftCertificateModel.builder()
                .name("Spa Package")
                .build();

        String requestBody = objectMapper.writeValueAsString(updateCertificate);

        mockMvc.perform(patch("/api/v0/certificates/" + CERTIFICATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void deleteByIdShouldReturnNoContentStatus() {
        mockMvc.perform(delete("/api/v0/certificates/" + CERTIFICATE_ID))
                .andExpect(status().isNoContent());
    }
}