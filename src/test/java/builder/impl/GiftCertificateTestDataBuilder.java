package builder.impl;

import builder.TestDataBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aGiftCertificate")
public class GiftCertificateTestDataBuilder implements TestDataBuilder<GiftCertificate> {
    private Long id = 0L;
    private String name = "";
    private String description = "";
    private BigDecimal price = BigDecimal.ZERO;
    private Integer duration = 0;
    private Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
    private Timestamp lastUpdateDate = Timestamp.valueOf(LocalDateTime.now());
    private List<Tag> tags = new ArrayList<>();

    @Override
    public GiftCertificate build() {
        return GiftCertificate.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .duration(duration)
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .tags(tags)
                .build();
    }
}
