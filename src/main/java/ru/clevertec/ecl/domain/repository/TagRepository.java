package ru.clevertec.ecl.domain.repository;

import ru.clevertec.ecl.domain.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<Tag> findAll(int page, int size);
    List<Tag> findByGiftCertificateId(long id);
    Optional<Tag> findById(long id);
    Tag insert(Tag tag);
    Tag insertAndAddToGiftCertificate(long tagCertificateId, Tag tag);
    void update(Long id, Tag tag);
    void delete(Long id);
    boolean existsById(Long id);
}
