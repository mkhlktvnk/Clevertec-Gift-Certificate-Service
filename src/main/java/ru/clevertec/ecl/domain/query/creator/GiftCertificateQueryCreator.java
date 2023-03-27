package ru.clevertec.ecl.domain.query.creator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        String searchQuery = createSearchQuery(pageable.getSort(), criteria);
        StringBuilder query = new StringBuilder(searchQuery);
        addLimitAndOffset(query, GIFT_CERTIFICATES_TABLE, pageable.getPageSize(), pageable.getPageNumber());
        return query.toString();
    }

    public String createSearchQuery(Sort sort, GiftCertificateCriteria criteria) {
        StringBuilder query = new StringBuilder(GiftCertificateQueries.FIND_ALL);

        if (criteria.getTagName() != null) {
            query.append(" ").append(GiftCertificateQueries.JOIN_ON_TAGS);
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

        addSortParams(query, GIFT_CERTIFICATES_TABLE, sort);

        return query.toString();
    }

    public String createSortedAndPaginatedQuery(Pageable pageable) {
        StringBuilder query = new StringBuilder(GiftCertificateQueries.FIND_ALL);

        addSortParams(query, GIFT_CERTIFICATES_TABLE, pageable.getSort());

        query.append(" LIMIT ").append(pageable.getPageSize())
                .append(" OFFSET ").append(pageable.getOffset());

        return query.toString();
    }

    private void addLimitAndOffset(StringBuilder query, String table, int limit, int offset) {
        query.append(" LIMIT ").append(limit)
                .append(" OFFSET ").append(offset);
    }

    private void addSortParams(StringBuilder query, String table, Sort sort) {
        sort.stream().forEach(sortParam ->
                addSortParam(query, table, sortParam.getProperty(), sortParam.getDirection().name())
        );
    }

    private void addSearchParamWithFullMatch(StringBuilder query, String table, String column, String value) {
        addWhereKeyword(query);
        addParamName(query, table, column);
        query.append("=").append("'").append(value).append("'");
    }

    private void addSearchParamWithPartialMatch(StringBuilder query, String table, String column, String value) {
        addWhereKeyword(query);
        addParamName(query, table, column);
        query.append(" LIKE ").append("'%").append(value).append("%'");
    }

    private void addSortParam(StringBuilder query, String table, String column, String direction) {
        addOrderKeyword(query);
        addParamName(query, table, column);
        query.append(" ").append(direction);
    }

    private void addParamName(StringBuilder query, String table, String column) {
        query.append(table).append(".").append(column);
    }

    private void addWhereKeyword(StringBuilder query) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
    }

    private void addOrderKeyword(StringBuilder query) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
    }
}
