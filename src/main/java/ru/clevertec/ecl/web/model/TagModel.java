package ru.clevertec.ecl.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
public class TagModel {
    @NotNull
    @Min(0)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @JsonProperty("name")
    private String name;
}
