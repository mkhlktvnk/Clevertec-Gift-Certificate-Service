package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.web.model.GiftCertificateModel;

import java.util.Collection;
import java.util.List;

@Mapper(uses = TagMapper.class)
public interface GiftCertificateMapper {
    GiftCertificate mapToEntity(GiftCertificateModel model);
    GiftCertificateModel mapToModel(GiftCertificate entity);
    List<GiftCertificateModel> mapToModel(Collection<GiftCertificate> entities);
}
