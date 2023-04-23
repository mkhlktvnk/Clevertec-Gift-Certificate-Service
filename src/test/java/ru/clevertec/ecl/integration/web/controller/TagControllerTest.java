package ru.clevertec.ecl.integration.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.web.model.TagModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class TagControllerTest extends BaseIntegrationTest {

    private static final Long TAG_ID = 1L;

    private static final Long USER_ID = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllByPageableShouldReturnCorrectCountOfTagsAndOkStatus() {
        int expectedSize = 3;

        mockMvc.perform(get("/api/v0/users?page=0&size=" + expectedSize))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(expectedSize));
    }

    @Test
    @SneakyThrows
    void findByIdShouldReturnCorrectTagAndOkStatus() {
        mockMvc.perform(get("/api/v0/tags/" + TAG_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TAG_ID));
    }

    @Test
    @SneakyThrows
    void findUserMostPopularTagWithTheHighestCostOfAllOrdersShouldReturnCorrectTagAndOkStatus() {
        TagModel expectedModel = TagModel.builder()
                .id(2L)
                .name("travel")
                .build();

        mockMvc.perform(get("/api/v0/tags/popular?userId=" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedModel.getId()))
                .andExpect(jsonPath("$.name").value(expectedModel.getName()));
    }

    @Test
    @SneakyThrows
    void saveShouldCreateNewTagAndReturnCorrectTagModelAndCreatedStatus() {
        TagModel expectedModel = TagModel.builder()
                .name("health")
                .build();

        String requestBody = objectMapper.writeValueAsString(expectedModel);

        mockMvc.perform(post("/api/v0/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(expectedModel.getName()));
    }

    @Test
    @SneakyThrows
    void updateByIdShouldUpdateTagByIdAndReturnNoContentStatus() {
        TagModel updateTag = TagModel.builder()
                .name("books")
                .build();

        String requestBody = objectMapper.writeValueAsString(updateTag);

        mockMvc.perform(patch("/api/v0/tags/" + TAG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void deleteByIdShouldDeleteTagAndReturnNoContentStatus() {
        mockMvc.perform(delete("/api/v0/tags/" + TAG_ID))
                .andExpect(status().isNoContent());
    }
}