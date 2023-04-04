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
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<GiftCertificate> findAll(Pageable pageable, Specification<GiftCertificate> specification) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<GiftCertificate> query =
                builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root =
                query.from(GiftCertificate.class);

        query.select(root)
                .where(specification.toPredicate(root, query, builder))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        return session.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(GiftCertificate.class, id));
    }

    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        Session session = sessionFactory.getCurrentSession();
        if (giftCertificate.getTags() != null) {
            List<Tag> mergedTags = mergeTags(giftCertificate.getTags(), session);
            giftCertificate.setTags(mergedTags);
        }
        session.persist(giftCertificate);
        session.flush();
        return giftCertificate;
    }

    @Override
    public void update(Long id, GiftCertificate updateCertificate) {
        Session session = sessionFactory.getCurrentSession();
        GiftCertificate certificate = session.get(GiftCertificate.class, id);

        certificate.setName(updateCertificate.getName());
        certificate.setDescription(updateCertificate.getDescription());
        certificate.setPrice(updateCertificate.getPrice());
        certificate.setDuration(updateCertificate.getDuration());

        if (updateCertificate.getTags() != null) {
            List<Tag> mergedTags = mergeTags(updateCertificate.getTags(), session);
            certificate.getTags().addAll(mergedTags);
        }
        session.flush();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        GiftCertificate certificate =
                session.get(GiftCertificate.class, id);
        session.remove(certificate);
        session.flush();
    }

    @Override
    public boolean existsById(Long id) {
        return sessionFactory.getCurrentSession()
                .get(GiftCertificate.class, id) != null;
    }

    private List<Tag> mergeTags(List<Tag> detachedTags, Session session) {
        return detachedTags.stream().map(session::merge).toList();
    }
}
