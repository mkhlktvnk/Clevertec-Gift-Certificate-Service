package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.domain.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
