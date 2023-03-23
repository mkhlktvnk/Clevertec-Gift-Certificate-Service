package ru.clevertec.ecl.domain.repository;

import ru.clevertec.ecl.domain.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    List<GiftCertificate> findAll(int page, int size);
    Optional<GiftCertificate> findById(Long id);
    GiftCertificate insert(GiftCertificate giftCertificate);
    void update(Long id, GiftCertificate giftCertificate);
    void delete(Long id);
    boolean existsById(Long id);
}
