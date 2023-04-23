package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.web.model.GiftCertificateModel;

import java.util.Collection;
import java.util.List;

@Mapper(uses = TagMapper.class)
public interface GiftCertificateMapper {
    @Mapping(source = "tagModels", target = "tags")
    GiftCertificate mapToEntity(GiftCertificateModel model);

    @Mapping(source = "tags", target = "tagModels")
    GiftCertificateModel mapToModel(GiftCertificate entity);

    List<GiftCertificateModel> mapToModel(Collection<GiftCertificate> entities);
}
