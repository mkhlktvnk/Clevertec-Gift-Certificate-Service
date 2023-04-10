package ru.clevertec.ecl.service.impl;

import builder.impl.GiftCertificateTestDataBuilder;
import builder.impl.OrderTestDataBuilder;
import builder.impl.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Order;
import ru.clevertec.ecl.domain.entity.User;
import ru.clevertec.ecl.domain.repository.OrderRepository;
import ru.clevertec.ecl.service.exception.ResourceNotFoundException;
import ru.clevertec.ecl.service.message.MessagesSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID = 2L;
    private static final Long CERTIFICATE_ID = 3L;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private MessagesSource messagesSource;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void checkFindAllByUserIdShouldReturnExpectedResult() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Order> expected = List.of(OrderTestDataBuilder.anOrder().build());
        doReturn(true).when(userService).existsById(USER_ID);
        doReturn(expected).when(orderRepository).findAllByUserId(USER_ID, pageable);

        List<Order> actual = orderService.findAllByUserId(USER_ID, pageable);

        verify(userService).existsById(USER_ID);
        verify(orderRepository).findAllByUserId(USER_ID, pageable);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindAllByUserIdShouldThrowResourceNotFoundException() {
        Pageable pageable = PageRequest.of(0, 1);
        doReturn(false).when(userService).existsById(USER_ID);

        assertThatThrownBy(() -> orderService.findAllByUserId(USER_ID, pageable))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkFindByIdShouldReturnExpectedResult() {
        Order expected = OrderTestDataBuilder.anOrder().build();
        doReturn(Optional.of(expected)).when(orderRepository).findById(ORDER_ID);

        Order actual = orderService.findById(ORDER_ID);

        verify(orderRepository).findById(ORDER_ID);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindByIdShouldThrowResourceNotFoundException() {
        doReturn(Optional.empty()).when(orderRepository).findById(ORDER_ID);

        assertThatThrownBy(() -> orderService.findById(ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void checkMakeOrderShouldMakeCorrectOrder() {
        User user = UserTestDataBuilder.anUser().build();
        GiftCertificate giftCertificate = GiftCertificateTestDataBuilder
                .aGiftCertificate().build();
        Order order = OrderTestDataBuilder.anOrder().withUser(user)
                .withGiftCertificate(giftCertificate).build();
        doReturn(user).when(userService).findById(USER_ID);
        doReturn(giftCertificate).when(giftCertificateService).findById(CERTIFICATE_ID);
        doReturn(order).when(orderRepository).save(any(Order.class));

        Order actual = orderService.makeOrder(USER_ID, CERTIFICATE_ID);

        verify(userService).findById(USER_ID);
        verify(giftCertificateService).findById(CERTIFICATE_ID);
        verify(orderRepository).save(any(Order.class));
        assertThat(actual.getUser()).isEqualTo(user);
        assertThat(actual.getGiftCertificate()).isEqualTo(giftCertificate);
    }

    @Test
    void checkDeleteByIdShouldDeleteOrder() {
        doReturn(true).when(orderRepository).existsById(ORDER_ID);

        orderService.deleteById(ORDER_ID);

        verify(orderRepository).existsById(ORDER_ID);
        verify(orderRepository).deleteById(ORDER_ID);
    }

    @Test
    void checkDeleteByIdShouldThrowResourceNotFoundException() {
        doReturn(false).when(orderRepository).existsById(ORDER_ID);

        assertThatThrownBy(() -> orderService.deleteById(ORDER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}