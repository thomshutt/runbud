package com.thomshutt.runbud.data;

import com.thomshutt.runbud.core.Comment;
import com.thomshutt.runbud.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

public class CommentDAO extends AbstractDAO<Comment> {

    public CommentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment get(Serializable id) {
        return super.get(id);
    }

    public List<Comment> listForRunId(String runId) throws HibernateException {
        return super.list(super.criteria().add(Restrictions.eq("runId", runId)));
    }

    @Override
    public Comment persist(Comment entity) throws HibernateException {
        return super.persist(entity);
    }

}