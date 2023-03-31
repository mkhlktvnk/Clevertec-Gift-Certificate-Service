package ru.clevertec.ecl.domain.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.domain.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Tag> findAll(Pageable pageable) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
            Root<Tag> root = query.from(Tag.class);

            query.select(root);
            query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

            return session.createQuery(query)
                    .setFirstResult(pageable.getPageNumber())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
        }
    }

    @Override
    public Optional<Tag> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Tag.class, id));
        }
    }

    @Override
    public Tag insert(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.persist(tag);
            session.flush();
            session.getTransaction().commit();
            return tag;
        }
    }

    @Override
    public void update(Long id, Tag updateTag) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Tag tag = session.get(Tag.class, id);
            tag.setName(updateTag.getName());
            session.flush();
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Tag tag = session.get(Tag.class, id);
            session.remove(tag);
            session.flush();
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean existsById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tag.class, id) != null;
        }
    }
}
