package ru.clevertec.ecl.domain.repository;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    List<GiftCertificate> findAllByCriteria(Pageable pageable, GiftCertificateCriteria criteria);
    List<GiftCertificate> findAllByPageable(Pageable pageable);
    Optional<GiftCertificate> findById(Long id);
    GiftCertificate insert(GiftCertificate giftCertificate);
    void update(Long id, GiftCertificate giftCertificate);
    void delete(Long id);
    boolean existsById(Long id);
}
