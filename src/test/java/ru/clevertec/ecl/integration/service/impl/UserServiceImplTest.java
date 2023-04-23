package ru.clevertec.ecl.integration.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessageKey;
import ru.clevertec.ecl.service.message.MessagesSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceImplTest extends BaseIntegrationTest {

    private static final Long CORRECT_ID = 1L;

    private static final Long INCORRECT_ID = 101L;

    @Autowired
    private UserService userService;

    @Autowired
    private MessagesSource messages;

    @Test
    void findAllByPageableShouldReturnCorrectCountOfUsers() {
        Pageable pageable = PageRequest.of(0, 3);

        List<User> users = userService.findAllByPageable(pageable);

        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    void findAllByPageableShouldReturnEmptyResultWhenPageIsGreaterThanUsersCount() {
        Pageable pageable = PageRequest.of(100, 3);

        List<User> users = userService.findAllByPageable(pageable);

        assertThat(users).isEmpty();
    }

    @Test
    void findByIdShouldReturnNotNullResult() {
        User user = userService.findById(CORRECT_ID);

        assertThat(user).isNotNull();
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenUserIdIsIncorrect() {
        assertThatThrownBy(() -> userService.findById(INCORRECT_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(messages.get(MessageKey.USER_NOT_FOUND));
    }
}