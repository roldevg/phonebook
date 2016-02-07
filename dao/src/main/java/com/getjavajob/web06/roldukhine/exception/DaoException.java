package com.getjavajob.web06.roldukhine.exception;

public class DaoException extends RuntimeException {
    public DaoException(Exception cause) {
        super(cause);
    }

    public DaoException(String s) {
        super(s);
    }
}
