package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> findAllByPageable(Pageable pageable);
    List<GiftCertificate> findAllBySortAndCriteria(Sort sort, GiftCertificateCriteria criteria);
    GiftCertificate getById(long id);
    GiftCertificate save(GiftCertificate giftCertificate);
    void updateById(long id, GiftCertificate giftCertificate);
    void deleteById(long id);
}
