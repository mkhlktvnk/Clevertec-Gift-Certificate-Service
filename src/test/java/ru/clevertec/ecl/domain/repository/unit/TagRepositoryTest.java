package ru.clevertec.ecl.domain.repository.unit;

import builder.impl.TagTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.mapper.TagMapper;
import ru.clevertec.ecl.domain.query.TagQueries;
import ru.clevertec.ecl.domain.repository.impl.TagRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagRepositoryImpl tagRepository;

    private static final Long ID = 1L;

    @Test
    void checkFindAllShouldReturnExpectedResultAndCallJdbcTemplate() {
        Integer page = 0;
        Integer size = 1;
        List<Tag> expected = List.of(TagTestDataBuilder.aTag().build());
        doReturn(expected).when(jdbcTemplate)
                .query(TagQueries.FIND_WITH_LIMIT_AND_OFFSET, tagMapper, page, size);

        List<Tag> actual = tagRepository.findAll(1, 0);

        verify(jdbcTemplate).query(TagQueries.FIND_WITH_LIMIT_AND_OFFSET, tagMapper, page, size);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindByIdShouldReturnActualResultAndCallJdbcTemplate() {
        Tag expected = TagTestDataBuilder.aTag().build();
        doReturn(expected).when(jdbcTemplate).queryForObject(TagQueries.FIND_BY_ID, tagMapper, ID);

        Optional<Tag> actual = tagRepository.findById(ID);

        verify(jdbcTemplate).queryForObject(TagQueries.FIND_BY_ID, tagMapper, ID);
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void checkExistsByIdShouldReturnTrueWhenResultGreaterThanZeroAndCallJdbcTemplate() {
        doReturn(1).when(jdbcTemplate)
                .queryForObject(TagQueries.SELECT_COUNT_BY_ID, Integer.class, ID);

        Boolean actual = tagRepository.existsById(ID);

        verify(jdbcTemplate).queryForObject(TagQueries.SELECT_COUNT_BY_ID, Integer.class, ID);
        assertThat(actual).isTrue();
    }

    @Test
    void checkExistsByIdShouldReturnFalseWhenResultIsZeroOrLessAndCallJdbcTemplate() {
        doReturn(0).when(jdbcTemplate)
                .queryForObject(TagQueries.SELECT_COUNT_BY_ID, Integer.class, ID);

        Boolean actual = tagRepository.existsById(ID);

        verify(jdbcTemplate).queryForObject(TagQueries.SELECT_COUNT_BY_ID, Integer.class, ID);
        assertThat(actual).isFalse();
    }

    @Test
    void checkUpdateShouldCallJdbcTemplate() {
        Tag tag = TagTestDataBuilder.aTag().build();

        tagRepository.update(ID, tag);

        verify(jdbcTemplate).update(TagQueries.UPDATE, tag.getName(), ID);
    }

    @Test
    void checkDeleteShouldCallJdbcTemplate() {
        tagRepository.delete(ID);

        verify(jdbcTemplate).update(TagQueries.DELETE_BY_ID, ID);
    }
}