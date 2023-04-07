package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllByPageable(Pageable pageable);

    User findById(long id);
}
