package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class RunDAO extends AbstractDAO<Run> {

    public RunDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Run get(Serializable id) {
        final Run run = super.get(id);
        return run.isCancelled() ? null : run;
    }

    public List<Run> list() throws HibernateException {
        return super.list(super.criteria().add(Restrictions.eq("isCancelled", false)));
    }

    public List<Run> listForInitiatingUser(User user) throws HibernateException {
        return super.list(super.criteria().add(Restrictions.eq("initiatingUserId", user.getUserId())));
    }

    @Override
    public Run persist(Run entity) throws HibernateException {
        return super.persist(entity);
    }

}
