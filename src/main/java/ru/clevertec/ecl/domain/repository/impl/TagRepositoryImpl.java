package ru.clevertec.ecl.domain.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.constant.column.TagColumns;
import ru.clevertec.ecl.domain.constant.table.Tables;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.query.TagQueries;
import ru.clevertec.ecl.domain.repository.TagRepository;
import ru.clevertec.ecl.domain.repository.exception.DomainException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Tag> mapper;

    @Override
    public List<Tag> findAll(Pageable pageable) {
        try {
            return jdbcTemplate.query(TagQueries.FIND_WITH_LIMIT_AND_OFFSET, mapper,
                    pageable.getPageSize(), pageable.getOffset());
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Tag> findByGiftCertificateId(long id) {
        try {
            return jdbcTemplate.query(TagQueries.FIND_TAGS_BY_GIFT_CERTIFICATE_ID, mapper, id);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Tag> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(TagQueries.FIND_BY_ID, mapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Tag insert(Tag tag) {
        try {
            long id = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(Tables.TAGS_TABLE)
                    .usingColumns(TagColumns.NAME)
                    .usingGeneratedKeyColumns(TagColumns.ID)
                    .executeAndReturnKey(Map.of(TagColumns.NAME, tag.getName()))
                    .longValue();
            return findById(id).orElseThrow(DomainException::new);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public Tag insertAndAddToGiftCertificate(long tagCertificateId, Tag tag) {
        try {
            Tag inserted = insert(tag);
            jdbcTemplate.update(TagQueries.ADD_TAG_TO_GIFT_CERTIFICATE,
                    tagCertificateId, inserted.getId());
            return inserted;
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Long id, Tag updateTag) {
        try {
            jdbcTemplate.update(TagQueries.UPDATE, updateTag.getName(), id);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update(TagQueries.DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            Integer result = jdbcTemplate.queryForObject(TagQueries.SELECT_COUNT_BY_ID,
                    Integer.class, id);
            return result > 0;
        } catch (DataAccessException e) {
            throw new DomainException(e.getMessage(), e);
        }
    }

    private Tag mapInsertResult(Map<String, Object> map) {
        return Tag.builder()
                .id((Long) map.get(TagColumns.ID))
                .name((String) map.get(TagColumns.NAME))
                .build();
    }
}
