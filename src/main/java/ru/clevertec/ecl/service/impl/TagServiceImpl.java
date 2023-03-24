package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.TagRepository;
import ru.clevertec.ecl.service.TagService;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Tag getById(long id) {
        return null;
    }

    @Override
    public Tag insert(Tag tag) {
        return null;
    }

    @Override
    public Tag insertAndAddToGiftCertificate(long giftCertificateId, Tag tag) {
        return null;
    }

    @Override
    public void updateById(long id, Tag tag) {

    }

    @Override
    public void deleteById(long id) {

    }
}
