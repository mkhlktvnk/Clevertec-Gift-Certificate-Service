package ru.clevertec.ecl.domain.extractor;

import java.util.Map;

public interface FieldExtractor<T> {
    Map<String, String> extract(T object);
}
