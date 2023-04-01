package ru.clevertec.ecl.domain.spec;

import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.entity.Tag;

@UtilityClass
public class GiftCertificateSpecifications {
    public static Specification<GiftCertificate> hasNameLike(String name) {
        return (root, query, builder) -> {
            if (name == null) {
                return builder.isTrue(builder.literal(true));
            }
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<GiftCertificate> hasDescriptionLike(String description) {
        return (root, query, builder) -> {
            if (description == null) {
                return builder.isTrue(builder.literal(true));
            }
            return builder.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<GiftCertificate> hasTagWithName(String tagName) {
        return (root, query, builder) -> {
            if (tagName == null) {
                return builder.isTrue(builder.literal(true));
            }
            Join<Tag, GiftCertificate> join = root.join("tags");
            return builder.equal(join.get("name"), tagName);
        };
    }
}
