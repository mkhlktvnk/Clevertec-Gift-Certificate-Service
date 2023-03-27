package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> findAllByPageableAndCriteria(Pageable pageable, GiftCertificateCriteria criteria);
    GiftCertificate findById(long id);
    GiftCertificate save(GiftCertificate giftCertificate);
    void updateById(long id, GiftCertificate giftCertificate);
    void deleteById(long id);
}
