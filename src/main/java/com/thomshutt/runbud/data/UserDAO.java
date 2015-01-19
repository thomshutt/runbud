package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User get(Serializable id) {
        return super.get(id);
    }

    public User getForEmail(String email) throws HibernateException {
        return super.uniqueResult(super.criteria().add(Restrictions.eq("email", email)));
    }

    @Override
    public User persist(User entity) throws HibernateException {
        return super.persist(entity);
    }

}