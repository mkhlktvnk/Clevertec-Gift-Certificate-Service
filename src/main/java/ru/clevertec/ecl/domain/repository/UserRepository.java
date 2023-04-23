package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
