package ru.clevertec.ecl.web.criteria;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GiftCertificateCriteria {

    @Size(min = 1)
    private String name;

    @Size(min = 1)
    private String description;

    private List<String> tagName;

}
