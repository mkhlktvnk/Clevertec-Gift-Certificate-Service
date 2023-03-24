package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

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
        return tagRepository.insertAndAddToGiftCertificate(giftCertificateId, tag);
    }

    @Override
    public void updateById(long id, Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag not found!");
        }
        tagRepository.delete(id);
    }
}
