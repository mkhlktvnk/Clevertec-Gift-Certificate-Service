package ru.clevertec.ecl.domain.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Tag {
    private Long id;
    private String name;
}
