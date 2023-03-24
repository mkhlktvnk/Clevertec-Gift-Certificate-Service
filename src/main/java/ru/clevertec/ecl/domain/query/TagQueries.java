package ru.clevertec.ecl.domain.query;

public class TagQueries {
    public static final String FIND_BY_ID = "SELECT * FROM tags WHERE id = ?;";

    public static final String FIND_WITH_LIMIT_AND_OFFSET = "SELECT * FROM tags LIMIT ? OFFSET ?;";

    public static final String INSERT = "INSERT INTO tags (name) VALUES ?;";

    public static final String UPDATE = "UPDATE tags SET name = ? WHERE id = ?;";

    public static final String DELETE_BY_ID = "DELETE FROM tags WHERE ID = ?;";

    public static final String SELECT_COUNT_BY_ID = "SELECT COUNT(*) FROM tags WHERE id = ?;";

    public static final String ADD_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO gift_certificates_tags " +
            "(gift_certificate_id, tag_id) VALUES(?, ?);";
}
