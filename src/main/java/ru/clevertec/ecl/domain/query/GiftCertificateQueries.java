package ru.clevertec.ecl.domain.query;

public class GiftCertificateQueries {
    public static final String FIND_BY_ID = "SELECT * FROM gift_certificates WHERE id = ?;";

    public static final String FIND_ALL_WITH_LIMIT_AND_OFFSET = "SELECT * FROM gift_certificates LIMIT ? OFFSET ?;";

    public static final String INSERT = "INSERT INTO gift_certificates (name, description, price, duration) " +
            "VALUES (?, ?, ?, ?);";

    public static final String UPDATE_BY_ID = "UPDATE gift_certificates " +
            "SET name = ?, description = ?, price = ?, duration = ? WHERE id = ?;";

    public static final String DELETE_BY_ID = "DELETE FROM gift_certificates WHERE id = ?;";
}
