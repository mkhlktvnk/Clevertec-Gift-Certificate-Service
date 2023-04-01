package ru.clevertec.ecl.domain.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<GiftCertificate> findAll(Pageable pageable, Specification<GiftCertificate> specification) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<GiftCertificate> query =
                    builder.createQuery(GiftCertificate.class);
            Root<GiftCertificate> root =
                    query.from(GiftCertificate.class);

            query.select(root)
                    .where(specification.toPredicate(root, query, builder))
                    .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

            return session.createQuery(query)
                    .setFirstResult(pageable.getPageNumber())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(GiftCertificate.class, id));
        }
    }

    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(giftCertificate);
            session.flush();
            session.getTransaction().commit();
            return giftCertificate;
        }
    }

    @Override
    public void update(Long id, GiftCertificate updateCertificate) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            GiftCertificate certificate = session.get(GiftCertificate.class, id);
            certificate.setName(updateCertificate.getName());
            certificate.setDescription(updateCertificate.getDescription());
            certificate.setPrice(updateCertificate.getPrice());
            certificate.setDuration(updateCertificate.getDuration());
            certificate.getTags().addAll(updateCertificate.getTags());
            session.flush();
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            GiftCertificate certificate = session.get(GiftCertificate.class, id);
            session.remove(certificate);
            session.flush();
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(GiftCertificate.class, id) != null;
        }
    }
}
