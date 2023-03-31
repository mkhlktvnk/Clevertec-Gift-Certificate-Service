package ru.clevertec.ecl.domain.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.constant.column.TagColumns;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.TagRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Tag> mapper;

    @Override
    public List<Tag> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tag> findByGiftCertificateId(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tag> findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tag insert(Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tag insertAndAddToGiftCertificate(long tagCertificateId, Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Long id, Tag updateTag) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean existsById(Long id) {
        throw new UnsupportedOperationException();
    }

    private Tag mapInsertResult(Map<String, Object> map) {
        return Tag.builder()
                .id((Long) map.get(TagColumns.ID))
                .name((String) map.get(TagColumns.NAME))
                .build();
    }
}
