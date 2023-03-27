package ru.clevertec.ecl.domain.repository;

import builder.impl.TagTestDataBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.mapper.TagMapper;
import ru.clevertec.ecl.domain.repository.exception.DomainException;
import ru.clevertec.ecl.domain.repository.impl.TagRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class TagRepositoryTest {
    private TagRepository tagRepository;
    private EmbeddedDatabase dataSource;
    private final TagMapper tagMapper = new TagMapper();

    private static final Long CORRECT_ID = 1L;

    private static final Long INCORRECT_ID = 1000L;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/scripts/test/schema.sql")
                .addScript("classpath:sql/scripts/test/data.sql")
                .build();
        tagRepository = new TagRepositoryImpl(new JdbcTemplate(dataSource), tagMapper);
    }

    @AfterEach
    void clear() {
        dataSource.shutdown();
    }

    @Test
    void checkFindAllShouldReturnCorrectNumberOfTags() {
        List<Tag> tags = tagRepository.findAll(0, 10);

        assertThat(tags).isNotEmpty();
    }

    @Test
    void findAllShouldReturnEmptyTagsList() {
        List<Tag> tags = tagRepository.findAll(1000, 10);

        assertThat(tags).isEmpty();
    }

    @Test
    void findByGiftCertificateIdShouldReturnNotEmptyList() {
        List<Tag> tags = tagRepository.findByGiftCertificateId(1L);

        assertThat(tags).isNotEmpty();
    }

    @Test
    void findByGiftCertificateIdShouldReturnEmptyList() {
        List<Tag> tags = tagRepository.findByGiftCertificateId(100L);

        assertThat(tags).isEmpty();
    }

    @Test
    void checkFindByIdShouldReturnNotEmptyResult() {
        Optional<Tag> tag = tagRepository.findById(1L);

        assertThat(tag).isPresent();
    }

    @Test
    void checkFindByIdShouldReturnEmptyResult() {
        Optional<Tag> tag = tagRepository.findById(100L);

        assertThat(tag).isNotPresent();
    }

    @Test
    void checkInsertShouldThrowDomainException() {
        Tag tag = TagTestDataBuilder.aTag()
                .withName(null)
                .build();

        assertThatThrownBy(() -> tagRepository.insert(tag))
                .isInstanceOf(DomainException.class);
    }

    @Test
    void checkUpdateShouldUpdateTagById() {
        Tag expected = TagTestDataBuilder.aTag()
                .withId(1L)
                .withName("new-tag-1")
                .build();

        tagRepository.update(1L, expected);

        Optional<Tag> actual = tagRepository.findById(1L);

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void checkDeleteShouldDeleteTagById() {
        tagRepository.delete(CORRECT_ID);

        Optional<Tag> actual = tagRepository.findById(CORRECT_ID);

        assertThat(actual).isEmpty();
    }

    @Test
    void checkExistsByIdShouldReturnTrue() {
        assertThat(tagRepository.existsById(CORRECT_ID)).isTrue();
    }

    @Test
    void checkExistsByIdShouldReturnFalse() {
        assertThat(tagRepository.existsById(INCORRECT_ID)).isFalse();
    }
}