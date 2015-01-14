package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.Run;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public class RunDAO extends AbstractDAO<Run> {

    public RunDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Run get(Serializable id) {
        return super.get(id);
    }

    public List<Run> list() throws HibernateException {
        return super.list(criteria());
    }

    @Override
    public Run persist(Run entity) throws HibernateException {
        return super.persist(entity);
    }

}
