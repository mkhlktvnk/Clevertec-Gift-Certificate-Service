package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.domain.repository.TagRepository;
import ru.clevertec.ecl.service.message.GiftCertificateMessages;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMessages giftCertificateMessages;


    @Override
    public List<GiftCertificate> findAllByPageableAndCriteria(Pageable pageable, GiftCertificateCriteria criteria) {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll(pageable, criteria);
        addTagsToGiftCertificates(certificates);
        return certificates;
    }

    @Override
    public GiftCertificate findById(long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(giftCertificateMessages.getNotFound()));
        giftCertificate.setTags(tagRepository.findByGiftCertificateId(id));
        return giftCertificate;
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        GiftCertificate insertedCertificate = giftCertificateRepository.insert(giftCertificate);
        if (giftCertificate.getTags() != null) {
            List<Tag> insertedTags = giftCertificate.getTags().stream()
                    .map(tag -> tagRepository.insertAndAddToGiftCertificate(insertedCertificate.getId(), tag))
                    .toList();
            insertedCertificate.setTags(insertedTags);
        }
        List<Tag> tags = tagRepository.findByGiftCertificateId(insertedCertificate.getId());
        insertedCertificate.setTags(tags);
        return insertedCertificate;
    }

    @Override
    @Transactional
    public void updateById(long id, GiftCertificate giftCertificate) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException(giftCertificateMessages.getNotFound());
        }
        if (giftCertificate.getTags() != null) {
            giftCertificate.getTags().forEach(tag -> tagRepository.insertAndAddToGiftCertificate(id, tag));
        }
        giftCertificateRepository.update(id, giftCertificate);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException(giftCertificateMessages.getNotFound());
        }
        giftCertificateRepository.delete(id);
    }

    private void addTagsToGiftCertificates(List<GiftCertificate> giftCertificates) {
        giftCertificates.forEach(giftCertificate ->
                giftCertificate.setTags(tagRepository.findByGiftCertificateId(giftCertificate.getId()))
        );
    }
}
