package ru.clevertec.ecl.integration.service.impl;

import builder.impl.TagTestDataBuilder;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessageKey;
import ru.clevertec.ecl.service.message.MessagesSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TagServiceImplTest extends BaseIntegrationTest {

    private static final Long CORRECT_TAG_ID = 1L;

    private static final Long INCORRECT_TAG_ID = 100L;

    private static final Long CORRECT_USER_ID = 2L;

    @Autowired
    private TagService tagService;

    @Autowired
    private MessagesSource messages;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAllByPageableShouldReturnCorrectNumberOfTags() {
        Pageable pageable = PageRequest.of(0, 3);

        List<Tag> tags = tagService.findAllByPageable(pageable);

        assertThat(tags.size()).isEqualTo(3);
    }

    @Test
    void findAllByPageableShouldReturnEmptyResultWhenPageIsGreaterThanTagsCount() {
        Pageable pageable = PageRequest.of(100, 3);

        List<Tag> tags = tagService.findAllByPageable(pageable);

        assertThat(tags).isEmpty();
    }

    @Test
    void findByIdShouldReturnNonNullResult() {
        Tag tag = tagService.findById(CORRECT_TAG_ID);

        assertThat(tag).isNotNull();
        assertThat(tag.getId()).isEqualTo(CORRECT_TAG_ID);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenTagIdIsNotCorrect() {
        assertThatThrownBy(() -> tagService.findById(INCORRECT_TAG_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.TAG_NOT_FOUND));
    }


    @Test
    void findUserMostPopularTagWithTheHighestCostOfAllOrdersShouldReturnNotNullResult() {
        Tag tag = tagService.findUserMostPopularTagWithTheHighestCostOfAllOrders(CORRECT_USER_ID);

        assertThat(tag).isNotNull();
    }

    @Test
    void insertShouldReturnTagWithNotNullId() {
        Tag tag = TagTestDataBuilder.aTag()
                .withName("new-tag")
                .build();

        Tag actual = tagService.insert(tag);

        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void updateByIdShouldUpdateTagName() {
        Tag updateTag = TagTestDataBuilder.aTag()
                .withName("new-tag-name")
                .build();

        tagService.updateById(CORRECT_TAG_ID, updateTag);
        Tag actual = tagService.findById(CORRECT_TAG_ID);

        assertThat(actual.getName()).isEqualTo(updateTag.getName());
    }

    @Test
    void updateByIdShouldThrowResourceNotFoundExceptionWhenTagIdIsIncorrect() {
        Tag updateTag = TagTestDataBuilder.aTag()
                .withName("new-tag-name")
                .build();

        assertThatThrownBy(() -> tagService.updateById(INCORRECT_TAG_ID, updateTag))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.TAG_NOT_FOUND));
    }
}