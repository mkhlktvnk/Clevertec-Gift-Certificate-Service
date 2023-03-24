package ru.clevertec.ecl.web.criteria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiftCertificateCriteria {
    @NotBlank
    @Size(min = 1)
    private String name;

    @NotBlank
    @Size(min = 1)
    private String description;

    @NotBlank
    @Size(min = 1)
    private String tagName;
}
