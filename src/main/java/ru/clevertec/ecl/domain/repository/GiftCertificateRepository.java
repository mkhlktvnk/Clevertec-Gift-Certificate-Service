package ru.clevertec.ecl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.GiftCertificate;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        JpaSpecificationExecutor<GiftCertificate> {

}
