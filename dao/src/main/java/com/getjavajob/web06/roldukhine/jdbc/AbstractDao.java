package com.getjavajob.web06.roldukhine.jdbc;

import com.getjavajob.web06.roldukhine.ConnectionPool;
import com.getjavajob.web06.roldukhine.entity.BaseEntity;
import com.getjavajob.web06.roldukhine.DaoException;
import com.getjavajob.web06.roldukhine.jdbc.api.CrudDao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends BaseEntity> implements CrudDao<T> {

    protected abstract String getTableName();

    protected abstract String getInsertStatement();

    protected abstract String getUpdateByIdStatement();

    protected abstract T createInstanceFromResult(ResultSet resultSet) throws SQLException;

    protected String getSelectAllStatement() {
        return "SELECT * FROM " + getTableName();
    }

    protected String getSelectByIdStatement() {
        return getSelectAllStatement() + " WHERE id = ?";
    }

    protected String getDeleteByIdStatement() {
        return "DELETE FROM " + getTableName() + " WHERE id = ?";
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @Override
    public void insert(T entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();

        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try (PreparedStatement prepareStatement = connection.prepareStatement(getInsertStatement())) {
            int j = 0;
            for (Field field : fields) {
                if (field.getType() != List.class) {
                    Method getMethod = null;
                    Object fieldValue;
                    try {
                        String name = "get" + capitalizeFirstLetter(field.getName());
                        getMethod = clazz.getMethod(name);
                    } catch (NoSuchMethodException e) {
                        throw new DaoException(e);
                    }
                    Object newValue = null;
                    try {
                        if (getMethod != null) {
                            fieldValue = getMethod.invoke(entity);
                            newValue = getValue(field, fieldValue);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new DaoException(e);
                    }
                    j++;
                    prepareStatement.setObject(j, newValue);
                }
            }
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                entity.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }

    private Object getValue(Field field, Object fieldValue) {
        Object newValue = null;
        if (!field.getType().isPrimitive() && !(field.getType() == String.class)
                && !(field.getType().isEnum())) {
            if (fieldValue != null) {
                newValue = ((BaseEntity) fieldValue).getId();
            }
        } else if (field.getType().isEnum()) {
            newValue = ((Enum) fieldValue).ordinal() + 1;
        } else {
            newValue = fieldValue;
        }
        return newValue;
    }

    @Override
    public void update(T entity) {
        long id = entity.getId();
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Object> values = new ArrayList<>();
        for (Field field : fields) {
            if (field.getType() != List.class) {
                Method getMethod = null;
                try {
                    getMethod = clazz.getMethod("get" + capitalizeFirstLetter(field.getName()));
                } catch (NoSuchMethodException e) {
                    throw new DaoException(e);
                }
                try {
                    if (getMethod != null) {
                        Object fieldValue = getMethod.invoke(entity);
                        Object newValue = getValue(field, fieldValue);
                        values.add(newValue);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new DaoException(e);
                }
            }
        }
        updateField(values, fields, id);
    }

    private void updateField(List<Object> values, Field[] fields, long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement(getUpdateByIdStatement())) {
            for (int i = 0; i < values.size(); i++) {
                prepareStatement.setObject(i + 1, values.get(i));
                prepareStatement.setLong(values.size() + 1, id);
            }
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }


    @Override
    public T get(long id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            try (PreparedStatement prepareStatement = connection.prepareStatement(getSelectByIdStatement())) {
                prepareStatement.setLong(1, id);
                try (ResultSet resultSet = prepareStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createInstanceFromResult(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void delete(T entity) {
        long id = entity.getId();
        Connection connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement prepareStatement = connection.prepareStatement(getDeleteByIdStatement())) {
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<T> getAll() {
        List<T> resultList = null;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            try (ResultSet rs = connection.createStatement().executeQuery(getSelectAllStatement())) {
                resultList = new ArrayList<>();
                while (rs.next()) {
                    resultList.add(createInstanceFromResult(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return resultList;
    }

}
