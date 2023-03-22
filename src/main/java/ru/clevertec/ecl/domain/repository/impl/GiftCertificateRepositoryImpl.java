package ru.clevertec.ecl.domain.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.query.GiftCertificateQueries;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.domain.repository.exception.DomainException;

import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<GiftCertificate> mapper;

    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        return jdbcTemplate.query(GiftCertificateQueries.FIND_ALL_WITH_LIMIT_AND_OFFSET,
                new Object[]{ size, page }, mapper);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GiftCertificateQueries.FIND_BY_ID,
                new Object[] { id }, mapper));
    }

    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(c -> c.prepareStatement(GiftCertificateQueries.INSERT,
                    Statement.RETURN_GENERATED_KEYS), keyHolder);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue())
                .orElseThrow(DomainException::new);
    }

    @Override
    public void update(Long id, GiftCertificate giftCertificate) {
        try {
            jdbcTemplate.update(GiftCertificateQueries.UPDATE_BY_ID,
                    giftCertificate.getName(),
                    giftCertificate.getDescription(),
                    giftCertificate.getPrice(),
                    giftCertificate.getDuration(),
                    giftCertificate.getId()
            );
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
}
