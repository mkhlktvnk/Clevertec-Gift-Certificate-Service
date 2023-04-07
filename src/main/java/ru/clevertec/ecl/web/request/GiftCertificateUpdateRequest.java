package ru.clevertec.ecl.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateUpdateRequest {

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String name;

}
