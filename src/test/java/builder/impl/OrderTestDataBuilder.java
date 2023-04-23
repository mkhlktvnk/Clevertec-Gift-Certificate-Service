package builder.impl;

import builder.TestDataBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Order;
import ru.clevertec.ecl.domain.entity.User;

import java.math.BigDecimal;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "anOrder")
public class OrderTestDataBuilder implements TestDataBuilder<Order> {
    private Long id = 0L;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private User user = UserTestDataBuilder.anUser().build();
    private GiftCertificate giftCertificate = GiftCertificateTestDataBuilder.aGiftCertificate().build();

    @Override
    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);

        return order;
    }
}
