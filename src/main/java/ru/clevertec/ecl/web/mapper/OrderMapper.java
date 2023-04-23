package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.clevertec.ecl.domain.entity.Order;
import ru.clevertec.ecl.web.model.OrderModel;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {
        UserMapper.class,
        GiftCertificateMapper.class
})
public interface OrderMapper {

    @Mappings(value = {
            @Mapping(target = "userModel", source = "user"),
            @Mapping(target = "giftCertificateModel", source = "giftCertificate")
    })
    OrderModel mapToModel(Order order);

    Order mapToEntity(OrderModel orderModel);

    List<OrderModel> mapToModel(Collection<Order> orders);
}
