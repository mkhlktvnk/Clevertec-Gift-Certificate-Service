package ru.clevertec.ecl.domain.query.creator;

public interface QueryCreator<T> {
    String createUpdateQuery(T object, String tableName);
}
