package ru.clevertec.ecl.service.impl;

import builder.impl.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.domain.repository.UserRepository;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessagesSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final Long ID = 1L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessagesSource messages;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void checkFindAllByPageableShouldReturnExpectedResult() {
        Pageable pageable = PageRequest.of(0, 1);
        List<User> users = List.of(UserTestDataBuilder.anUser().build());
        Page<User> expected = new PageImpl<>(users);
        doReturn(expected).when(userRepository).findAll(pageable);

        List<User> actual = userService.findAllByPageable(pageable);

        verify(userRepository).findAll(pageable);
        assertThat(actual).isEqualTo(expected.getContent());
    }

    @Test
    void checkFindByIdShouldReturnExpectedResult() {
        User expected = UserTestDataBuilder.anUser().build();
        doReturn(Optional.of(expected)).when(userRepository).findById(ID);

        User actual = userService.findById(ID);

        verify(userRepository).findById(ID);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindByIdShouldThrowResourceNotFoundException() {
        doReturn(Optional.empty()).when(userRepository).findById(ID);

        assertThatThrownBy(() -> userService.findById(ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
