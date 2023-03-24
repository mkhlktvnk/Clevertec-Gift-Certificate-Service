package ru.clevertec.ecl.domain.repository;

import ru.clevertec.ecl.domain.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<Tag> findAll(int page, int size);
    Optional<Tag> findById(long id);
    Tag insert(Tag tag);
    Tag addTagToGiftCertificate(long tagCertificateId, Tag tag);
    void delete(Long id);
}
