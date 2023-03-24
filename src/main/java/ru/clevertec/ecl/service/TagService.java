package ru.clevertec.ecl.service;

import ru.clevertec.ecl.domain.entity.Tag;

public interface TagService {
    Tag getById(long id);
    Tag insert(Tag tag);
    Tag insertAndAddToGiftCertificate(long giftCertificateId, Tag tag);
    void updateById(long id, Tag tag);
    void deleteById(long id);
}
