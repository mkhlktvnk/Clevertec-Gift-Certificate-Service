package ru.clevertec.ecl.web.controller;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.web.mapper.UserMapper;
import ru.clevertec.ecl.web.model.UserModel;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @GetMapping("/users")
    public List<UserModel> findAllByPageable(@PageableDefault Pageable pageable) {
        List<User> users = userService.findAllByPageable(pageable);
        return mapper.mapToModel(users);
    }

    @GetMapping("/users/{id}")
    public UserModel findById(@PathVariable Long id) {
        return mapper.mapToModel(userService.findById(id));
    }
}
