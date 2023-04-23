package ru.clevertec.ecl.domain.repository.impl;

import builder.impl.TagTestDataBuilder;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import config.HibernateTestConfig;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Rollback
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateTestConfig.class)
class TagRepositoryImplTest {
    private static final Long CORRECT_ID = 2L;

    private static final Long INCORRECT_ID = 1000L;

    @Autowired
    private SessionFactory sessionFactory;

    private final TagRepository tagRepository = new TagRepositoryImpl(sessionFactory);

    @Test
    void checkFindAllShouldReturnNotEmptyResult() {
        List<Tag> tags = tagRepository.findAll(PageRequest.of(0, 10));

        assertThat(tags).isNotEmpty();
    }

    @Test
    void checkFindAllShouldReturnEmptyResult() {
        List<Tag> tags = tagRepository.findAll(PageRequest.of(1000, 1000));

        assertThat(tags).isEmpty();
    }

    @Test
    void checkFindByIdShouldReturnNotEmptyResult() {
        Optional<Tag> tag = tagRepository.findById(CORRECT_ID);

        assertThat(tag).isPresent();
    }

    @Test
    void checkByIdShouldReturnEmptyResult() {
        Optional<Tag> tag = tagRepository.findById(INCORRECT_ID);

        assertThat(tag).isNotPresent();
    }

    @Test
    void checkInsetShouldReturnSavedResult() {
        Tag tag = TagTestDataBuilder.aTag().withName("new-name").build();

        Tag saved = tagRepository.insert(tag);

        assertThat(saved.getName()).isEqualTo(tag.getName());
    }

    @Test
    void checkUpdateShouldUpdateTag() {
        Tag updateTag = TagTestDataBuilder.aTag().withName("new-name").build();

        tagRepository.update(CORRECT_ID, updateTag);
        Optional<Tag> actual = tagRepository.findById(CORRECT_ID);

        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo(updateTag.getName());
    }

    @Test
    void checkDeleteShouldDeleteTag() {
        tagRepository.delete(CORRECT_ID);
        Optional<Tag> actual = tagRepository.findById(CORRECT_ID);

        assertThat(actual).isNotPresent();
    }
}