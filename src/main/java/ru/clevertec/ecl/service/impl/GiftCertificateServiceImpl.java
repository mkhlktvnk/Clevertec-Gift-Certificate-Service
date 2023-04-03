package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.domain.spec.GiftCertificateSpecifications;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.GiftCertificateMessages;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMessages giftCertificateMessages;

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> findAllByPageableAndCriteria(Pageable pageable, GiftCertificateCriteria criteria) {
        Specification<GiftCertificate> specification = Specification.anyOf(
                GiftCertificateSpecifications.hasNameLike(criteria.getName()),
                GiftCertificateSpecifications.hasDescriptionLike(criteria.getDescription()),
                GiftCertificateSpecifications.hasTagWithName(criteria.getTagName())
        );
        return giftCertificateRepository.findAll(pageable, specification);
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(giftCertificateMessages.getNotFound()));
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        return giftCertificateRepository.insert(giftCertificate);
    }

    @Override
    @Transactional
    public void updateById(long id, GiftCertificate updateCertificate) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException(giftCertificateMessages.getNotFound());
        }
        giftCertificateRepository.update(id, updateCertificate);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException(giftCertificateMessages.getNotFound());
        }
        giftCertificateRepository.delete(id);
    }
}
