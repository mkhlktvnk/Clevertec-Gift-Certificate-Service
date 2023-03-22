package ru.clevertec.ecl.domain.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.domain.columns.GiftCertificateColumns;
import ru.clevertec.ecl.domain.entity.GiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(GiftCertificateColumns.ID))
                .name(rs.getString(GiftCertificateColumns.NAME))
                .description(rs.getString(GiftCertificateColumns.DESCRIPTION))
                .price(rs.getBigDecimal(GiftCertificateColumns.PRICE))
                .duration(rs.getInt(GiftCertificateColumns.DURATION))
                .createDate(rs.getTimestamp(GiftCertificateColumns.CREATE_DATE))
                .lastUpdateDate(rs.getTimestamp(GiftCertificateColumns.LAST_UPDATE_DATE))
                .build();
    }
}
