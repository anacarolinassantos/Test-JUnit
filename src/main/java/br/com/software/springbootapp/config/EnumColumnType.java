package br.com.software.springbootapp.config;

import br.com.software.springbootapp.helpper.PersistableEnum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EnumColumnType<T extends Enum<T>> extends EnumType<T> {

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        if (isPersistableEnum()) {

            var classOfResult = getClassOfResult();
            Object db = null;
            if (Character.class.equals(classOfResult)) {
                var str = rs.getString(names[0]);
                if (Objects.nonNull(str) && !str.isEmpty()) {
                    db = str.charAt(0);
                }
            } else {
                db = rs.getObject(names[0], classOfResult);
            }

            return getFromDb(db);
        }

        return super.nullSafeGet(rs, names, session, owner);
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (isPersistableEnum()) {
            setValue(ps, index, parseToDb((PersistableEnum<?>) value));
            return;
        }
        super.nullSafeSet(ps, value, index, session);
    }

    @Override
    public String objectToSQLString(Object value) {
        if (isPersistableEnum()) {
            return String.valueOf(parseToDb((PersistableEnum<?>) value));
        }
        return super.objectToSQLString(value);
    }

    @Override
    public String toXMLString(Object value) {
        if (isPersistableEnum()) {
            var str = parseToDb((PersistableEnum<?>) value);
            return Objects.isNull(str) ? null : String.valueOf(str);
        }
        return super.toXMLString(value);
    }

    @Override
    public Object fromXMLString(String xmlValue) {
        if (isPersistableEnum()) {
            return getFromDb(xmlValue);
        }
        return super.fromXMLString(xmlValue);
    }

    @Override
    public int[] sqlTypes() {
        if (isPersistableEnum()) {
            return new int[]{getType()};

        }
        return super.sqlTypes();
    }

    private Object parseToDb(PersistableEnum<?> attribute) {
        return Objects.isNull(attribute) ? null : attribute.getValue();
    }

    @SuppressWarnings("unchecked")
    private T getFromDb(Object dbData) {

        if (Objects.isNull(dbData)) {
            return null;
        }

        Class<T> clazz = (Class<T>) returnedClass();
        var enums = clazz.getEnumConstants();
        for (T e : enums) {
            if (((PersistableEnum<?>) e).getValue().equals(dbData)) {
                return e;
            }
        }

        return null;
    }

    private void setValue(PreparedStatement ps, int index, Object value) throws SQLException {
        if (Objects.isNull(value)) {
            ps.setObject(index, null);
        } else {
            ps.setObject(index, value);
        }
    }

    private Class<?> getClassOfResult() {
        return PersistableEnum.getClassOfResult(getPersistableEnumClass());
    }

    private int getType() {
        return PersistableEnum.getType(getPersistableEnumClass());
    }

    @SuppressWarnings("all")
    private Class<PersistableEnum<?>> getPersistableEnumClass() {
        return (Class<PersistableEnum<?>>) ((Class<?>) returnedClass());
    }

    private boolean isPersistableEnum() {
        return PersistableEnum.class.isAssignableFrom(returnedClass());
    }
}
