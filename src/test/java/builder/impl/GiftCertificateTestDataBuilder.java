package builder.impl;

import builder.TestDataBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
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
    private Instant createDate = Instant.now();
    private Instant lastUpdateDate = Instant.now();
    private List<Tag> tags = new ArrayList<>();

    @Override
    public GiftCertificate build() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(lastUpdateDate);
        giftCertificate.setTags(tags);
        return giftCertificate;
    }
}
