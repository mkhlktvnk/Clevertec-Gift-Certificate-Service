package ru.clevertec.ecl.service;

import ru.clevertec.ecl.domain.entity.GiftCertificate;

public interface GiftCertificateService {
    GiftCertificate getById(long id);
    GiftCertificate save(GiftCertificate giftCertificate);
    void updateById(long id, GiftCertificate giftCertificate);
    void deleteById(long id);
    boolean existsById(long id);
}
