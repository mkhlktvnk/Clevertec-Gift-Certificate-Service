package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.GiftCertificateService;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;

    @Override
    public GiftCertificate getById(long id) {
        return null;
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public void updateById(long id, GiftCertificate giftCertificate) {

    }

    @Override
    public void deleteById(long id) {

    }
}
