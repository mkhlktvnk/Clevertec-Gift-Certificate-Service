package ru.clevertec.ecl.domain.query.creator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.domain.constant.column.GiftCertificateColumns;
import ru.clevertec.ecl.domain.constant.column.TagColumns;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.extractor.FieldExtractor;
import ru.clevertec.ecl.domain.query.GiftCertificateQueries;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.Map;
import java.util.StringJoiner;

import static ru.clevertec.ecl.domain.constant.table.Tables.GIFT_CERTIFICATES_TABLE;
import static ru.clevertec.ecl.domain.constant.table.Tables.TAGS_TABLE;

@Component
@RequiredArgsConstructor
public class GiftCertificateQueryCreator {
    private static final String JOIN = "LEFT JOIN gift_certificates_tags ON" +
            " gift_certificates.id = gift_certificates_tags.gift_certificate_id " +
            "LEFT JOIN tags ON tags.id = gift_certificates_tags.tag_id";
    private final FieldExtractor<GiftCertificate> extractor;

    public String createUpdateQuery(GiftCertificate object) {
        StringJoiner joiner = new StringJoiner(",");
        Map<String, String> fields = extractor.extract(object);
        String id = fields.get(GiftCertificateColumns.ID);

        fields.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(GiftCertificateColumns.ID))
                .filter(entry -> !"null".equals(entry.getValue()))
                .forEach(entry -> joiner.add(entry.getKey() + "=" + '\'' + entry.getValue() + '\''));

        return "UPDATE " + GIFT_CERTIFICATES_TABLE + " SET " + joiner + " WHERE " + GiftCertificateColumns.ID + " = " + id;
    }

    public String createSearchQuery(Pageable pageable, GiftCertificateCriteria criteria) {
        StringBuilder query = new StringBuilder(GiftCertificateQueries.FIND_ALL);

        if (criteria.getTagName() != null) {
            query.append(" ").append(JOIN);
            addSearchParamWithFullMatch(query, TAGS_TABLE, TagColumns.NAME, criteria.getTagName());
        }
        if (criteria.getName() != null) {
            addSearchParamWithPartialMatch(query, GIFT_CERTIFICATES_TABLE,
                    GiftCertificateColumns.NAME, criteria.getName());
        }
        if (criteria.getDescription() != null) {
            addSearchParamWithPartialMatch(query, GIFT_CERTIFICATES_TABLE,
                    GiftCertificateColumns.DESCRIPTION, criteria.getDescription());
        }

        pageable.getSort().stream().forEach(sortParam ->
                addSortParam(query, GIFT_CERTIFICATES_TABLE, sortParam.getProperty(), sortParam.getDirection().name())
        );

        query.append(" LIMIT ").append(pageable.getPageSize())
                .append(" OFFSET ").append(pageable.getOffset());

        return query.toString();
    }

    private void addSearchParamWithFullMatch(StringBuilder query, String table, String column, String value) {
        checkConditionKeyword(query);
        addParamName(query, table, column);
        query.append("=").append("'").append(value).append("'");
    }

    private void addSearchParamWithPartialMatch(StringBuilder query, String table, String column, String value) {
        checkConditionKeyword(query);
        addParamName(query, table, column);
        query.append(" LIKE ").append("'%").append(value).append("%'");
    }

    private void addSortParam(StringBuilder query, String table, String column, String direction) {
        checkOrderKeyword(query);
        addParamName(query, table, column);
        query.append(" ").append(direction);
    }

    private void addParamName(StringBuilder query, String table, String column) {
        query.append(table).append(".").append(column);
    }

    private void checkConditionKeyword(StringBuilder query) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
    }

    private void checkOrderKeyword(StringBuilder query) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
    }
}
