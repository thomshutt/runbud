package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.UserCredentials;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;

public class UserCredentialsDAO extends AbstractDAO<UserCredentials> {

    public UserCredentialsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UserCredentials get(Serializable id) {
        return super.get(id);
    }

    public UserCredentials getForToken(String token) throws HibernateException {
        final UserCredentials userCredentials = super.uniqueResult(super.criteria().add(Restrictions.eq("token", token)));
        if(userCredentials == null || userCredentials.getTokenExpiryUtcMillis() < System.currentTimeMillis()) {
            return null;
        }
        return userCredentials;
    }

    @Override
    public UserCredentials persist(UserCredentials entity) throws HibernateException {
        return super.persist(entity);
    }

}