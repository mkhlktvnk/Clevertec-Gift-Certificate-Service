package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessageKey;
import ru.clevertec.ecl.service.message.MessagesSource;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;

import static ru.clevertec.ecl.domain.spec.GiftCertificateSpecifications.*;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final MessagesSource messages;

    @Override
    public List<GiftCertificate> findAllByPageableAndCriteria(Pageable pageable, GiftCertificateCriteria criteria) {
        Specification<GiftCertificate> specification = Specification.where(hasNameLike(criteria.getName()))
                .and(hasDescriptionLike(criteria.getDescription()))
                .and(hasTagWithNameIn(criteria.getTagName()));

        return giftCertificateRepository.findAll(specification, pageable).getContent();
    }

    @Override
    public GiftCertificate findById(long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messages.get(MessageKey.GIFT_CERTIFICATE_NOT_FOUND)
                ));
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        return giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public void updateById(long id, GiftCertificate updateCertificate) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    messages.get(MessageKey.GIFT_CERTIFICATE_NOT_FOUND)
            );
        }
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messages.get(MessageKey.GIFT_CERTIFICATE_NOT_FOUND)
                ));
        giftCertificate.setName(updateCertificate.getName());
        giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    messages.get(MessageKey.GIFT_CERTIFICATE_NOT_FOUND)
            );
        }
        giftCertificateRepository.deleteById(id);
    }
}
