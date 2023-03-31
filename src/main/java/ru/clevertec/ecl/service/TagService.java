package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAllByPageable(Pageable pageable);

    Tag findById(long id);

    Tag insert(Tag tag);

    void updateById(long id, Tag updateTag);

    void deleteById(long id);
}
