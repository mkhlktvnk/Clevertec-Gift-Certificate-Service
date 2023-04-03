package ru.clevertec.ecl.domain.repository.impl;

import builder.impl.GiftCertificateTestDataBuilder;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.config.HibernateTestConfig;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.domain.spec.GiftCertificateSpecifications;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Rollback
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateTestConfig.class)
class GiftCertificateRepositoryImplTest {
    private static final Long CORRECT_ID = 2L;

    private static final Long INCORRECT_ID = 1000L;

    @Autowired
    private SessionFactory sessionFactory;

    private final GiftCertificateRepository repository = new GiftCertificateRepositoryImpl(sessionFactory);

    @Test
    void checkFindAllShouldReturnNotEmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Specification<GiftCertificate> specification = Specification.allOf(
                GiftCertificateSpecifications.hasNameLike("cert"),
                GiftCertificateSpecifications.hasDescriptionLike("desc"),
                GiftCertificateSpecifications.hasTagWithName("name")
        );
        List<GiftCertificate> giftCertificates = repository.findAll(pageable, specification);

        assertThat(giftCertificates).isNotEmpty();
    }

    @Test
    void checkFindAllShouldReturnEmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Specification<GiftCertificate> specification = Specification.allOf(
                GiftCertificateSpecifications.hasNameLike("incorrect-name"),
                GiftCertificateSpecifications.hasDescriptionLike("incorrect-descr"),
                GiftCertificateSpecifications.hasTagWithName("incorrect-tag")
        );
        List<GiftCertificate> giftCertificates = repository.findAll(pageable, specification);

        assertThat(giftCertificates).isNotEmpty();
    }

    @Test
    void checkFindByIdShouldReturnNotEmptyResult() {
        Optional<GiftCertificate> giftCertificate = repository.findById(CORRECT_ID);

        assertThat(giftCertificate).isPresent();
    }

    @Test
    void checkFindByIdShouldReturnEmptyResult() {
        Optional<GiftCertificate> giftCertificate = repository.findById(INCORRECT_ID);

        assertThat(giftCertificate).isNotPresent();
    }

    @Test
    void checkInsertShouldReturnSavedCertificate() {
        GiftCertificate giftCertificate = GiftCertificateTestDataBuilder.aGiftCertificate()
                .withName("name")
                .withDescription("description")
                .withPrice(BigDecimal.valueOf(120.20))
                .withDuration(30)
                .build();

        GiftCertificate actual = repository.insert(giftCertificate);

        assertThat(actual.getName()).isEqualTo(giftCertificate.getName());
        assertThat(actual.getDescription()).isEqualTo(giftCertificate.getDescription());
        assertThat(actual.getPrice()).isEqualTo(giftCertificate.getPrice());
        assertThat(actual.getDuration()).isEqualTo(giftCertificate.getDuration());
    }

    @Test
    void checkDeleteShouldDeleteCertificate() {
        repository.delete(CORRECT_ID);
        Optional<GiftCertificate> actual = repository.findById(CORRECT_ID);

        assertThat(actual).isNotPresent();
    }
}