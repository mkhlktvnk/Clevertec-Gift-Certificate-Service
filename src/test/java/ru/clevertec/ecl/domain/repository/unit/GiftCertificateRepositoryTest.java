package ru.clevertec.ecl.domain.repository.unit;

import builder.impl.GiftCertificateCriteriaTestDataBuilder;
import builder.impl.GiftCertificateTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.domain.query.GiftCertificateQueries;
import ru.clevertec.ecl.domain.query.creator.GiftCertificateQueryCreator;
import ru.clevertec.ecl.domain.repository.impl.GiftCertificateRepositoryImpl;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateRepositoryTest {

    private static final Long ID = 1L;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private GiftCertificateMapper mapper;
    @Mock
    private GiftCertificateQueryCreator creator;
    @InjectMocks
    private GiftCertificateRepositoryImpl giftCertificateRepository;

    @Test
    void checkFindAllShouldReturnActualResultAndCallJdbcTemplate() {
        String query = "";
        Pageable pageable = PageRequest.of(0, 1);
        GiftCertificateCriteria criteria = GiftCertificateCriteriaTestDataBuilder.aGiftCertificateCriteria().build();
        List<GiftCertificate> expected = List.of(GiftCertificateTestDataBuilder.aGiftCertificate().build());
        doReturn(query).when(creator).createSearchQuery(pageable, criteria);
        doReturn(expected).when(jdbcTemplate).query(query, mapper);

        List<GiftCertificate> actual = giftCertificateRepository.findAll(pageable, criteria);

        verify(jdbcTemplate).query(query, mapper);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindByIdShouldReturnExpectedResultAndCallJdbcTemplate() {
        GiftCertificate expected = GiftCertificateTestDataBuilder.aGiftCertificate().build();
        doReturn(expected).when(jdbcTemplate)
                .queryForObject(GiftCertificateQueries.FIND_BY_ID, mapper, ID);

        Optional<GiftCertificate> actual = giftCertificateRepository.findById(ID);

        verify(jdbcTemplate).queryForObject(GiftCertificateQueries.FIND_BY_ID, mapper, ID);
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    void checkExistsByIdShouldReturnTrueWhenResultIsGreaterThanZeroAndCallJdbcTemplate() {
        doReturn(1).when(jdbcTemplate)
                .queryForObject(GiftCertificateQueries.SELECT_COUNT_BY_ID, Integer.class, ID);

        Boolean actual = giftCertificateRepository.existsById(ID);

        verify(jdbcTemplate).queryForObject(GiftCertificateQueries.SELECT_COUNT_BY_ID, Integer.class, ID);
        assertThat(actual).isTrue();
    }

    @Test
    void checkExistsByIdShouldReturnFalseWhenResultIsZeroOrLessAndCallJdbcTemplate() {
        doReturn(0).when(jdbcTemplate)
                .queryForObject(GiftCertificateQueries.SELECT_COUNT_BY_ID, Integer.class, ID);

        Boolean actual = giftCertificateRepository.existsById(ID);

        verify(jdbcTemplate).queryForObject(GiftCertificateQueries.SELECT_COUNT_BY_ID, Integer.class, ID);
        assertThat(actual).isFalse();
    }

    @Test
    void checkUpdateShouldCallJdbcTemplate() {
        String query = "";
        GiftCertificate giftCertificate = GiftCertificateTestDataBuilder.aGiftCertificate().build();
        doReturn(query).when(creator).createUpdateQuery(giftCertificate);

        giftCertificateRepository.update(ID, giftCertificate);

        verify(jdbcTemplate).update(query);
    }

    @Test
    void checkDeleteShouldCallJdbcTemplate() {
        giftCertificateRepository.delete(ID);

        verify(jdbcTemplate).update(GiftCertificateQueries.DELETE_BY_ID, ID);
    }
}