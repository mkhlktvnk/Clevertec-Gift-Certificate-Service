package ru.clevertec.ecl.web.model;

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
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
}
