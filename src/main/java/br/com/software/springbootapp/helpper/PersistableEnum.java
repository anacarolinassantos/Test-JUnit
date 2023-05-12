package br.com.software.springbootapp.helpper;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Types;

public interface PersistableEnum<E> extends Serializable {

    E getValue();

    default Class<?> getClassOfResult() {
        return getClassOfResult(getClass());
    }

    default int getType() {
        return getType(getClass());
    }

    static <E, T extends PersistableEnum<E>> T getByValue(E value, Class<T> tClass) {
        for (T enumConstant : tClass.getEnumConstants()) {
            if (enumConstant.getValue().equals(value)) {
                return enumConstant;
            }
        }
        return null;
    }

    static <S extends PersistableEnum<?>> Class<?> getClassOfResult(Class<S> clazz) {
        return Utils.getClassOfTypeInterface(clazz, PersistableEnum.class, 0);
    }

    static <S extends PersistableEnum<?>> int getType(Class<S> enumClass) {
        Class<?> clazz = getClassOfResult(enumClass);
        if (String.class.equals(clazz)) {
            return Types.VARCHAR;
        } else if (Integer.class.equals(clazz)) {
            return Types.INTEGER;
        } else if (Long.class.equals(clazz) || BigInteger.class.equals(clazz)) {
            return Types.BIGINT;
        } else if (Short.class.equals(clazz)) {
            return Types.SMALLINT;
        } else  if (Character.class.equals(clazz)) {
            return Types.CHAR;
        }
        return Types.OTHER;
    }


}
