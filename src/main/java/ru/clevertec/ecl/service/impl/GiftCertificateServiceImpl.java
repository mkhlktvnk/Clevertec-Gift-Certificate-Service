package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    @Override
    public GiftCertificate getById(long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gift certificate not found!"));
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        GiftCertificate insertedCertificate = giftCertificateRepository.insert(giftCertificate);
        List<Tag> insertedTags = giftCertificate.getTags().stream()
                .map(tag -> tagService.insertAndAddToGiftCertificate(insertedCertificate.getId(), tag))
                .toList();
        insertedCertificate.setTags(insertedTags);
        return insertedCertificate;
    }

    @Override
    public void updateById(long id, GiftCertificate giftCertificate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        if (!giftCertificateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Gift certificate not found!");
        }
        giftCertificateRepository.delete(id);
    }
}
