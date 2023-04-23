package builder.impl;

import builder.TestDataBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.domain.entity.Order;
import ru.clevertec.ecl.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "anUser")
public class UserTestDataBuilder implements TestDataBuilder<User> {
    private Long id = 0L;
    private String username = "";
    private String email = "";
    private List<Order> orders = new ArrayList<>();

    @Override
    public User build() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setOrders(orders);
        return user;
    }
}
