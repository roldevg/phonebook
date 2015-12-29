package com.getjavajob.web06.roldukhine;

public class DaoException extends RuntimeException {
    public DaoException(Exception cause) {
        super(cause);
    }

    public DaoException(String s) {
        super(s);
    }
}
