package ru.clevertec.ecl.web.mapper;

import builder.impl.GiftCertificateTestDataBuilder;
import builder.impl.TagTestDataBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.web.model.GiftCertificateModel;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GiftCertificateMapperTest {
    private final GiftCertificateMapper mapper = Mappers.getMapper(GiftCertificateMapper.class);

    @ParameterizedTest
    @MethodSource("provideGiftCertificateModels")
    void mapToModel(GiftCertificate giftCertificate) {
        GiftCertificateModel mappedModel = mapper.mapToModel(giftCertificate);
        assertAll(
                () -> assertThat(mappedModel.getId()).isEqualTo(giftCertificate.getId()),
                () -> assertThat(mappedModel.getName()).isEqualTo(giftCertificate.getName()),
                () -> assertThat(mappedModel.getDescription()).isEqualTo(giftCertificate.getDescription()),
                () -> assertThat(mappedModel.getPrice()).isEqualTo(giftCertificate.getPrice()),
                () -> assertThat(mappedModel.getDuration()).isEqualTo(giftCertificate.getDuration()),
                () -> assertThat(mappedModel.getTagModels().size()).isEqualTo(giftCertificate.getTags().size())
        );
    }

    private static Stream<GiftCertificate> provideGiftCertificateModels() {
        return Stream.of(
                GiftCertificateTestDataBuilder.aGiftCertificate().withId(1L).withName("name-1")
                        .withTags(singletonList(TagTestDataBuilder.aTag().build()))
                        .withPrice(BigDecimal.valueOf(100.1))
                        .withDuration(10)
                        .build(),
                GiftCertificateTestDataBuilder.aGiftCertificate().withId(2L).withName("name-2")
                        .withTags(singletonList(TagTestDataBuilder.aTag().build()))
                        .withPrice(BigDecimal.valueOf(100.2))
                        .withDuration(10)
                        .build(),
                GiftCertificateTestDataBuilder.aGiftCertificate().withId(3L).withName("name-3")
                        .withTags(singletonList(TagTestDataBuilder.aTag().build()))
                        .withPrice(BigDecimal.valueOf(100.3))
                        .withDuration(10)
                        .build(),
                GiftCertificateTestDataBuilder.aGiftCertificate().withId(4L).withName("name-4")
                        .withTags(singletonList(TagTestDataBuilder.aTag().build()))
                        .withPrice(BigDecimal.valueOf(100.4))
                        .withDuration(10)
                        .build(),
                GiftCertificateTestDataBuilder.aGiftCertificate().withId(5L).withName("name-5")
                        .withTags(singletonList(TagTestDataBuilder.aTag().build()))
                        .withPrice(BigDecimal.valueOf(100.5))
                        .withDuration(10)
                        .build()
        );
    }
}