package ru.clevertec.ecl.domain.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.domain.repository.GiftCertificateRepository;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<GiftCertificate> findAll(Pageable pageable, GiftCertificateCriteria criteria) {
        throw new UnsupportedOperationException();
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
