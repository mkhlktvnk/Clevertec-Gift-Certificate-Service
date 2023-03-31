package ru.clevertec.ecl.domain.repository.impl;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}
