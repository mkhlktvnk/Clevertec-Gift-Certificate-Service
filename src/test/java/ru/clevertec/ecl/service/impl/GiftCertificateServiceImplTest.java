package ru.clevertec.ecl.service.impl;

import builder.impl.GiftCertificateCriteriaTestDataBuilder;
import builder.impl.GiftCertificateTestDataBuilder;
import builder.impl.TagTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.GiftCertificateMessages;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final Long ID = 1L;

    private static final GiftCertificateCriteria criteria = GiftCertificateCriteriaTestDataBuilder
            .aGiftCertificateCriteria()
            .withName("some-name")
            .withDescription("some-description")
            .withTagName("some-tag-name")
            .build();

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private GiftCertificateMessages tagMessages;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void checkGetByPageableAndCriteriaShouldReturnExpectedResultAndCallRepository() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Tag> tags = List.of(TagTestDataBuilder.aTag().build());
        List<GiftCertificate> expected = List.of(
                GiftCertificateTestDataBuilder.aGiftCertificate().withTags(tags).build()
        );
        doReturn(expected).when(giftCertificateRepository).findAll(any(Pageable.class), any());

        List<GiftCertificate> actual = giftCertificateService
                .findAllByPageableAndCriteria(pageable, criteria);

        verify(giftCertificateRepository).findAll(any(Pageable.class), any());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetByIdShouldReturnExpectedResultAndCallRepository() {
        GiftCertificate expected = GiftCertificateTestDataBuilder
                .aGiftCertificate().build();
        doReturn(Optional.of(expected)).when(giftCertificateRepository).findById(ID);

        GiftCertificate actual = giftCertificateService.findById(ID);

        verify(giftCertificateRepository).findById(ID);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetByIdShouldThrowResourceNotFoundException() {
        doReturn(Optional.empty()).when(giftCertificateRepository).findById(ID);

        assertThatThrownBy(() -> giftCertificateService.findById(ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkSaveShouldReturnExpectedResultAndCallRepository() {
        GiftCertificate expected = GiftCertificateTestDataBuilder
                .aGiftCertificate().build();
        doReturn(expected).when(giftCertificateRepository).insert(expected);

        GiftCertificate actual = giftCertificateService.save(expected);

        verify(giftCertificateRepository).insert(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkUpdateByShouldCallRepositoryTwice() {
        GiftCertificate updateCertificate = GiftCertificateTestDataBuilder
                .aGiftCertificate().build();
        doReturn(true).when(giftCertificateRepository).existsById(ID);

        giftCertificateService.updateById(ID, updateCertificate);

        verify(giftCertificateRepository).existsById(ID);
        verify(giftCertificateRepository).update(ID, updateCertificate);
    }

    @Test
    void checkByIdShouldThrowResourceNotFoundException() {
        GiftCertificate giftCertificate = GiftCertificateTestDataBuilder.aGiftCertificate().build();
        doReturn(false).when(giftCertificateRepository).existsById(ID);

        assertThatThrownBy(() -> giftCertificateService.updateById(ID, giftCertificate))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkDeleteByIdShouldCallRepository() {
        doReturn(true).when(giftCertificateRepository).existsById(ID);

        giftCertificateService.deleteById(ID);

        verify(giftCertificateRepository).existsById(ID);
        verify(giftCertificateRepository).delete(ID);
    }

    @Test
    void checkDeleteByIdShouldThrowResourceNotFoundException() {
        doReturn(false).when(giftCertificateRepository).existsById(ID);

        assertThatThrownBy(() -> giftCertificateService.deleteById(ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}