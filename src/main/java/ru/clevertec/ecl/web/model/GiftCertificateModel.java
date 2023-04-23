package ru.clevertec.ecl.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GiftCertificateModel {
    @NotNull
    @Min(0)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(value = "createDate", access = JsonProperty.Access.READ_ONLY)
    private String createDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(value = "lastUpdateDate", access = JsonProperty.Access.READ_ONLY)
    private String lastUpdateDate;

    @JsonProperty("tags")
    private List<TagModel> tagModels;
}
