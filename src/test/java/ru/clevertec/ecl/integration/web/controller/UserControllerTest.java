package ru.clevertec.ecl.integration.web.controller;

import builder.impl.UserTestDataBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllByPageableShouldReturnCorrectCountOfUsersAndOkStatus() {
        int expectedSize = 3;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/users?page=0&size=" + expectedSize))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(expectedSize));
    }

    @Test
    @SneakyThrows
    void findByIdShouldReturnCorrectUserAndOkStatus() {
        User expectedUser = UserTestDataBuilder.anUser()
                .withId(1L)
                .withUsername("Alice")
                .withEmail("alice@example.com")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/users/" + expectedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.username").value(expectedUser.getUsername()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()));
    }
}