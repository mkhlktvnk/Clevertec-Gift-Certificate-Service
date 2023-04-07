package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
