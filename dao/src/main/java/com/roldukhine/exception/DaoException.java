package com.roldukhine.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class DaoException extends RuntimeException {

    public DaoException(Exception cause) {
        super(cause);
        logger.debug("create dao exception {} with cause", cause);
    }

    public DaoException(String s) {
        super(s);
    }
}
