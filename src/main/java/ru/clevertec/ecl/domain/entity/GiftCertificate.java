package ru.clevertec.ecl.domain.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<Tag> tags;
}
