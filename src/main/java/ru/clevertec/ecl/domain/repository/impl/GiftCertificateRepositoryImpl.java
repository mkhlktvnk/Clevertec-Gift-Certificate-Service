package ru.clevertec.ecl.domain.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
import java.time.LocalDateTime;
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
    public Optional<GiftCertificate> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(GiftCertificateQueries.FIND_BY_ID,
                    new Object[]{id}, mapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(GiftCertificateQueries.INSERT,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setBigDecimal(3, giftCertificate.getPrice());
                ps.setInt(4, giftCertificate.getDuration());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
        return mapInsertResult(keyHolder.getKeys());
    }

    @Override
    public void update(Long id, GiftCertificate giftCertificate) {
        try {
            giftCertificate.setId(id);
            String query = queryCreator.createUpdateQuery(giftCertificate);
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
        boolean isExists;
        try {
            Integer result = jdbcTemplate.queryForObject(GiftCertificateQueries.SELECT_COUNT_BY_ID,
                    Integer.class, id);
            isExists = result > 0;
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
        return isExists;
    }

    private GiftCertificate mapInsertResult(Map<String, Object> map) {
        return GiftCertificate.builder()
                .id((Long) map.get(GiftCertificateColumns.ID))
                .name((String) map.get(GiftCertificateColumns.NAME))
                .description((String) map.get(GiftCertificateColumns.DESCRIPTION))
                .price((BigDecimal) map.get(GiftCertificateColumns.PRICE))
                .duration((Integer) map.get(GiftCertificateColumns.DURATION))
                .createDate((LocalDateTime) map.get(GiftCertificateColumns.CREATE_DATE))
                .lastUpdateDate((LocalDateTime) map.get(GiftCertificateColumns.LAST_UPDATE_DATE))
                .build();
    }
}
