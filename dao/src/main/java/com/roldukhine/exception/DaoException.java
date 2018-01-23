package com.roldukhine.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(DaoException.class);

    public DaoException(Exception cause) {
        super(cause);
        logger.debug("create dao exception {} with cause", cause);
    }

    public DaoException(String s) {
        super(s);
    }
}
