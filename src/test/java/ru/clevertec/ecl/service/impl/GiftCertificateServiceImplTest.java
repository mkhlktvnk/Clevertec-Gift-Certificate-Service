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
import ru.clevertec.ecl.domain.repository.TagRepository;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static final Long ID = 1L;

    @Test
    void checkGetGiftCertificatesShouldReturnExpectedResultAndCallRepository() {
        Pageable pageable = PageRequest.of(0, 1);
        GiftCertificateCriteria criteria = GiftCertificateCriteriaTestDataBuilder.aGiftCertificateCriteria().build();
        List<GiftCertificate> expected = List.of(GiftCertificateTestDataBuilder.aGiftCertificate().build());
        List<Tag> tags = List.of(TagTestDataBuilder.aTag().build());
        doReturn(expected).when(giftCertificateRepository).findAll(pageable, criteria);
        doReturn(tags).when(tagRepository).findByGiftCertificateId(anyLong());

        List<GiftCertificate> actual = giftCertificateService.getGiftCertificates(pageable, criteria);

        verify(giftCertificateRepository).findAll(pageable, criteria);
        verify(tagRepository).findByGiftCertificateId(anyLong());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetByIdShouldReturnExpectedResultAndCallRepository() {
        List<Tag> tags = List.of(TagTestDataBuilder.aTag().build());
        GiftCertificate expected = GiftCertificateTestDataBuilder.aGiftCertificate()
                .withTags(tags)
                .build();
        doReturn(Optional.of(expected)).when(giftCertificateRepository).findById(ID);
        doReturn(tags).when(tagRepository).findByGiftCertificateId(ID);

        GiftCertificate actual = giftCertificateService.getById(ID);

        verify(giftCertificateRepository).findById(ID);
        verify(tagRepository, times(tags.size())).findByGiftCertificateId(ID);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkGetByIdShouldThrowResourceNotFoundException() {
        doReturn(Optional.empty()).when(giftCertificateRepository).findById(ID);

        assertThatThrownBy(() -> giftCertificateService.getById(ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkSaveShouldReturnExpectedResultAndCallBothRepositories() {
        Tag tag = TagTestDataBuilder.aTag().build();
        List<Tag> tags = singletonList(tag);
        GiftCertificate expected = GiftCertificateTestDataBuilder.aGiftCertificate()
                .withId(ID).withTags(tags).build();
        doReturn(expected).when(giftCertificateRepository).insert(expected);
        doReturn(tags).when(tagRepository).findByGiftCertificateId(ID);

        GiftCertificate actual = giftCertificateService.save(expected);

        verify(tagRepository, times(tags.size()))
                .insertAndAddToGiftCertificate(eq(expected.getId()), eq(tag));
        verify(giftCertificateRepository).insert(expected);
        verify(tagRepository).findByGiftCertificateId(ID);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkUpdateByShouldCallRepositoryTwice() {
        Tag tag = TagTestDataBuilder.aTag().build();
        GiftCertificate giftCertificate = GiftCertificateTestDataBuilder.aGiftCertificate()
                .withTags(singletonList(tag))
                .build();
        int expectedTagRepositoryCalls = giftCertificate.getTags().size();
        doReturn(true).when(giftCertificateRepository).existsById(ID);

        giftCertificateService.updateById(ID, giftCertificate);

        verify(giftCertificateRepository).existsById(ID);
        verify(tagRepository, times(expectedTagRepositoryCalls))
                .insertAndAddToGiftCertificate(eq(ID), eq(tag));
        verify(giftCertificateRepository).update(ID, giftCertificate);
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