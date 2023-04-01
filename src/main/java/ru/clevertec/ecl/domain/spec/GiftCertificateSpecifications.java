package ru.clevertec.ecl.domain.spec;

import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;

@UtilityClass
public class GiftCertificateSpecifications {
    public static Specification<GiftCertificate> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null :
                        criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<GiftCertificate> hasDescriptionLike(String description) {
        return (root, query, criteriaBuilder) ->
                description == null ? null :
                        criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<GiftCertificate> hasTagWithName(String tagName) {
        return (root, query, criteriaBuilder) -> {
            Join<Tag, GiftCertificate> join = root.join("tags");
            return tagName == null ? null : criteriaBuilder.equal(join.get("name"), tagName);
        };
    }
}
