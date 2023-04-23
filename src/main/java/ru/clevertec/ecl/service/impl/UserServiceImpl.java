package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.domain.repository.UserRepository;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessageKey;
import ru.clevertec.ecl.service.message.MessagesSource;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MessagesSource messages;

    @Override
    public List<User> findAllByPageable(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messages.get(MessageKey.USER_NOT_FOUND)
                ));
    }

    @Override
    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

}
