package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.web.model.UserModel;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    UserModel mapToModel(User user);

    User mapToEntity(UserModel userModel);

    List<UserModel> mapToModel(Collection<User> users);
}
