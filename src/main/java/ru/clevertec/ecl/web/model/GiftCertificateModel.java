package ru.clevertec.ecl.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GiftCertificateModel {
    @NotNull
    @Min(0)
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Size(min = 1, max = 1000)
    @JsonProperty("description")
    private String description;

    @Digits(integer = 40, fraction = 2)
    @JsonProperty("price")
    private BigDecimal price;

    @Min(1)
    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("tags")
    private List<TagModel> tagModels;
}
