package ru.clevertec.ecl.domain.query.creator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.domain.columns.GiftCertificateColumns;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.extractor.FieldExtractor;
import ru.clevertec.ecl.domain.query.creator.QueryCreator;

import java.util.Map;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class GiftCertificateQueryCreator implements QueryCreator<GiftCertificate> {
    private final FieldExtractor<GiftCertificate> extractor;

    @Override
    public String createUpdateQuery(GiftCertificate object, String tableName) {
        StringJoiner joiner = new StringJoiner(",");
        Map<String, String> fields = extractor.extract(object);
        String id = fields.get(GiftCertificateColumns.ID);

        fields.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(GiftCertificateColumns.ID))
                .filter(entry -> !"null".equals(entry.getValue()))
                .forEach(entry -> joiner.add(entry.getKey() + "=" + '\'' + entry.getValue() + '\''));

        return "UPDATE " + tableName + " SET " + joiner + " WHERE " + GiftCertificateColumns.ID + " = " + id;
    }
}
