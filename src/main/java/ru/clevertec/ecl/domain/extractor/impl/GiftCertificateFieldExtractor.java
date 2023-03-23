package ru.clevertec.ecl.domain.extractor.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.ecl.domain.columns.GiftCertificateColumns;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.extractor.FieldExtractor;

import java.util.Map;

@Component
public class GiftCertificateFieldExtractor implements FieldExtractor<GiftCertificate> {
    @Override
    public Map<String, String> extract(GiftCertificate object) {
        return Map.ofEntries(
                Map.entry(GiftCertificateColumns.ID, String.valueOf(object.getId())),
                Map.entry(GiftCertificateColumns.NAME, String.valueOf(object.getName())),
                Map.entry(GiftCertificateColumns.DESCRIPTION, String.valueOf(object.getDescription())),
                Map.entry(GiftCertificateColumns.PRICE, String.valueOf(object.getPrice())),
                Map.entry(GiftCertificateColumns.DURATION, String.valueOf(object.getId()))
        );
    }
}
