package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "SELECT t.* FROM tags t " +
            "INNER JOIN gift_certificates_tags gct ON t.id = gct.tag_id " +
            "INNER JOIN gift_certificates gc ON gct.gift_certificate_id = gc.id " +
            "INNER JOIN orders o ON gc.id = o.gift_certificate_id " +
            "INNER JOIN users u ON o.user_id = u.id " +
            "WHERE u.id = :userId " +
            "GROUP BY t.id " +
            "ORDER BY SUM(o.total_price) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Tag> findUserMostPopularTagWithTheHighestCostOfAllOrders(@Param("userId") Long userId);

}
