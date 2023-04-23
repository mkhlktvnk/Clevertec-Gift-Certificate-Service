package ru.clevertec.ecl.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderModel {

    @NotNull
    @JsonProperty(value = "totalPrice", access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalPrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(value = "purchaseTime", access = JsonProperty.Access.READ_ONLY)
    private String purchaseTime;

}
