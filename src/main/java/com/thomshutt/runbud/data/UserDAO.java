package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

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

    public List<User> list() throws HibernateException {
        return super.list(criteria());
    }

    @Override
    public User persist(User entity) throws HibernateException {
        return super.persist(entity);
    }

}