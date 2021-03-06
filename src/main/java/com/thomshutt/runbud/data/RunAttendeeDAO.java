package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.RunAttendee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class RunAttendeeDAO extends AbstractDAO<RunAttendee> {

    public RunAttendeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public RunAttendee get(Serializable id) {
        return super.get(id);
    }

    public List<RunAttendee> listForRunId(long runId) throws HibernateException {
        return super.list(
                criteria()
                        .add(Restrictions.eq("runId", runId))
                        .add(Restrictions.eq("attending", true))
        );
    }

    public RunAttendee getForRunIdAndUserId(long runId, long userId) throws HibernateException {
        return uniqueResult(
                    criteria()
                        .add(Restrictions.eq("runId", runId))
                        .add(Restrictions.eq("userId", userId))
        );
    }

    @Override
    public RunAttendee persist(RunAttendee entity) throws HibernateException {
        return super.persist(entity);
    }



}
