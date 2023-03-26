package ru.clevertec.ecl.domain.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.domain.constant.column.GiftCertificateColumns;
import ru.clevertec.ecl.domain.entity.GiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(GiftCertificateColumns.ID))
                .name(rs.getString(GiftCertificateColumns.NAME))
                .description(rs.getString(GiftCertificateColumns.DESCRIPTION))
                .price(rs.getBigDecimal(GiftCertificateColumns.PRICE))
                .duration(rs.getInt(GiftCertificateColumns.DURATION))
                .createDate(rs.getObject(GiftCertificateColumns.CREATE_DATE, LocalDateTime.class))
                .lastUpdateDate(rs.getObject(GiftCertificateColumns.LAST_UPDATE_DATE, LocalDateTime.class))
                .build();
    }
}
