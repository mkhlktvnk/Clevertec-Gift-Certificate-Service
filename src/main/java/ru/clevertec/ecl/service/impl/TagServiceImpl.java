package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.domain.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Override
    public List<Tag> getTags(Pageable pageable) {
        return tagRepository.findAll(pageable.getPageNumber(), pageable.getPageSize());
    }

    @Override
    public Tag getById(long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found!"));
    }

    @Override
    public Tag insert(Tag tag) {
        return tagRepository.insert(tag);
    }

    @Override
    public Tag insertAndAddToGiftCertificate(long giftCertificateId, Tag tag) {
        if (!giftCertificateRepository.existsById(giftCertificateId)) {
            throw new ResourceNotFoundException("Gift certificate not found!");
        }
        return tagRepository.insertAndAddToGiftCertificate(giftCertificateId, tag);
    }

    @Override
    public void updateById(long id, Tag tag) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found!");
        }
        tagRepository.update(id, tag);
    }

    @Override
    public void deleteById(long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found!");
        }
        tagRepository.delete(id);
    }
}
