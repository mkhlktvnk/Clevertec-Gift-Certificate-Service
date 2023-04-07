package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t "
            + "WHERE t IN (SELECT tg FROM GiftCertificate gc "
            + "JOIN gc.tags tg "
            + "WHERE gc.id IN (SELECT o.giftCertificate.id FROM Order o "
            + "WHERE o.user.id IN (SELECT o.user.id FROM Order o GROUP BY o.user.id ORDER BY SUM(o.totalPrice) DESC)) "
            + "GROUP BY t.id "
            + "ORDER BY COUNT(t.id) DESC) "
            + "LIMIT 1")
    Optional<Tag> findUserMostPopularTagWithTheHighestCostOfAllOrders();

}
