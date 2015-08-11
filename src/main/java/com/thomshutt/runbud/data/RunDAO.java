package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
        return super.list(
                super.criteria()
                        .add(Restrictions.eq("isCancelled", false))
                        .add(Restrictions.gt(
                                        "date",
                                        DateTime.now(DateTimeZone.forOffsetHours(0)).getMillis()
                                )
                        )
        );
    }

    public List<Run> listForInitiatingUser(User user) throws HibernateException {
        return super.list(
                super.criteria()
                        .add(
                                Restrictions.eq(
                                        "initiatingUserId",
                                        user.getUserId()
                                )
                        )
                        .add(
                                Restrictions.gt(
                                        "date",
                                        DateTime.now(DateTimeZone.forOffsetHours(0)).getMillis()
                                )
                        )
        );
    }

    @Override
    public Run persist(Run entity) throws HibernateException {
        return super.persist(entity);
    }

}
