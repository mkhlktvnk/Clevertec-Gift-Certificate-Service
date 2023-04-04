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
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);

        query.select(root)
                .orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        return session.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Tag.class, id));
    }

    @Override
    public Tag insert(Tag tag) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(tag);
        session.flush();
        return tag;
    }

    @Override
    public void update(Long id, Tag updateTag) {
        Session session = sessionFactory.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        tag.setName(updateTag.getName());
        session.flush();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        session.remove(tag);
        session.flush();
    }

    @Override
    public boolean existsById(Long id) {
        return sessionFactory.getCurrentSession()
                .get(Tag.class, id) != null;
    }
}
