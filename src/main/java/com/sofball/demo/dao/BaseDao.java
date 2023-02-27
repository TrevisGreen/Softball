package com.sofball.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDao {

    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    protected EntityManager em;
}
