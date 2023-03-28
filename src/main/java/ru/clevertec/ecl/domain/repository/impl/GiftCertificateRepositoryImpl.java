package ru.clevertec.ecl.domain.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.constant.column.GiftCertificateColumns;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.query.GiftCertificateQueries;
import ru.clevertec.ecl.domain.query.creator.GiftCertificateQueryCreator;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.domain.repository.exception.DomainException;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<GiftCertificate> mapper;
    private final GiftCertificateQueryCreator queryCreator;


    @Override
    public List<GiftCertificate> findAll(Pageable pageable, GiftCertificateCriteria criteria) {
        return jdbcTemplate.query(queryCreator.createSearchQuery(pageable, criteria), mapper);
    }

    @Override
    public List<GiftCertificate> findAllBySortAndCriteria(Sort sort, GiftCertificateCriteria criteria) {
        return jdbcTemplate.query(queryCreator.createSearchQuery(sort, criteria), mapper);
    }

    @Override
    public List<GiftCertificate> findAllByPageable(Pageable pageable) {
        return jdbcTemplate.query(queryCreator.createSortedAndPaginatedQuery(pageable), mapper);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(GiftCertificateQueries.FIND_BY_ID, mapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(GiftCertificateQueries.INSERT,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setBigDecimal(3, giftCertificate.getPrice());
                ps.setInt(4, giftCertificate.getDuration());
                return ps;
            }, keyHolder);
            return mapInsertResult(keyHolder.getKeys());
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Long id, GiftCertificate updateCertificate) {
        try {
            updateCertificate.setId(id);
            String query = queryCreator.createUpdateQuery(updateCertificate);
            jdbcTemplate.update(query);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update(GiftCertificateQueries.DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            Integer result = jdbcTemplate.queryForObject(GiftCertificateQueries.SELECT_COUNT_BY_ID,
                    Integer.class, id);
            return result > 0;
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    private GiftCertificate mapInsertResult(Map<String, Object> map) {
        Timestamp createDate = (Timestamp) map.get(GiftCertificateColumns.CREATE_DATE);
        Timestamp lastUpdateDate = (Timestamp) map.get(GiftCertificateColumns.LAST_UPDATE_DATE);

        return GiftCertificate.builder()
                .id((Long) map.get(GiftCertificateColumns.ID))
                .name((String) map.get(GiftCertificateColumns.NAME))
                .description((String) map.get(GiftCertificateColumns.DESCRIPTION))
                .price((BigDecimal) map.get(GiftCertificateColumns.PRICE))
                .duration((Integer) map.get(GiftCertificateColumns.DURATION))
                .createDate(createDate.toInstant())
                .lastUpdateDate(lastUpdateDate.toInstant())
                .build();
    }
}
