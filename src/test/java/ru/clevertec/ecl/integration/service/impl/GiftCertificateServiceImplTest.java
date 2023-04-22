package ru.clevertec.ecl.integration.service.impl;

import builder.impl.GiftCertificateCriteriaTestDataBuilder;
import builder.impl.GiftCertificateTestDataBuilder;
import builder.impl.TagTestDataBuilder;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessageKey;
import ru.clevertec.ecl.service.message.MessagesSource;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GiftCertificateServiceImplTest extends BaseIntegrationTest {

    private static final Long CORRECT_ID = 1L;

    private static final Long INCORRECT_ID = 101L;

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private MessagesSource messages;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAllByPageableAndCriteriaShouldReturnCorrectCountOfCertificates() {
        Pageable pageable = PageRequest.of(0, 3);
        GiftCertificateCriteria criteria = GiftCertificateCriteriaTestDataBuilder
                .aGiftCertificateCriteria()
                .withTagName(List.of("food", "travel", "spa"))
                .build();

        List<GiftCertificate> certificates = giftCertificateService
                .findAllByPageableAndCriteria(pageable, criteria);

        assertThat(certificates.size()).isEqualTo(1);
    }

    @Test
    void findByIdShouldReturnNotNullResult() {
        GiftCertificate certificate = giftCertificateService.findById(CORRECT_ID);

        assertThat(certificate).isNotNull();
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdIsIncorrect() {
        assertThatThrownBy(() -> giftCertificateService.findById(INCORRECT_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.GIFT_CERTIFICATE_NOT_FOUND));
    }

    @Test
    void saveShouldReturnCertificateWithNotNullIdAndSaveTags() {
        GiftCertificate certificate = GiftCertificateTestDataBuilder.aGiftCertificate()
                .withName("Gym Membership")
                .withDescription("One month gym membership at Anytime Fitness")
                .withPrice(BigDecimal.valueOf(50))
                .withDuration(30)
                .withTags(List.of(
                        TagTestDataBuilder.aTag().withName("Fitness").build(),
                        TagTestDataBuilder.aTag().withName("Health").build()
                ))
                .build();

        GiftCertificate actual = giftCertificateService.save(certificate);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getTags().size()).isEqualTo(2);
    }


    @Test
    void updateByIdShouldUpdateCertificateName() {
        String name = "Spa Day Gift Certificate";
        GiftCertificate updateCertificate = GiftCertificateTestDataBuilder.aGiftCertificate()
                .withName(name)
                .build();

        giftCertificateService.updateById(CORRECT_ID, updateCertificate);
        entityManager.flush();
        GiftCertificate actual = giftCertificateService.findById(CORRECT_ID);

        assertThat(actual.getName()).isEqualTo(name);
    }
}