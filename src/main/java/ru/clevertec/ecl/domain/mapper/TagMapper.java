package ru.clevertec.ecl.domain.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.domain.columns.TagColumns;
import ru.clevertec.ecl.domain.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong(TagColumns.ID))
                .name(rs.getString(TagColumns.NAME))
                .build();
    }
}
