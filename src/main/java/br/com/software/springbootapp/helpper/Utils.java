package br.com.software.springbootapp.helpper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Utils {

    @SuppressWarnings("unchecked")
    public static <C> Class<C> getClassOfType(Class<?> clazz, Integer typeIndex) {
        Type thisType = clazz.getGenericSuperclass();

        if (thisType instanceof ParameterizedType) {
            return (Class<C>) ((ParameterizedType) thisType).getActualTypeArguments()[typeIndex];
        } else if (thisType instanceof Class) {
            return (Class<C>) ((ParameterizedType) ((Class<?>) thisType).getGenericSuperclass()).getActualTypeArguments()[typeIndex];
        } else {
            throw new IllegalArgumentException("Problema ao encontrar a classe para: " + clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static <C> Class<C> getClassOfTypeInterface(Class<?> clazz, Class<?> interfaceClass, Integer typeIndex) {
        Type[] types = clazz.getGenericInterfaces();
        Type thisType = clazz.getGenericSuperclass();

        for (Type type : types) {
            if (type.getTypeName().contains(interfaceClass.getTypeName())) {
                thisType = type;
            }
        }

        if (thisType instanceof ParameterizedType) {
            return (Class<C>) ((ParameterizedType) thisType).getActualTypeArguments()[typeIndex];
        } else if (thisType instanceof Class) {
            return (Class<C>) ((ParameterizedType) ((Class<?>) thisType).getGenericSuperclass()).getActualTypeArguments()[typeIndex];
        } else {
            throw new IllegalArgumentException("Problema ao encontrar a classe para: " + clazz);
        }
    }

    public static String readFileAsString(FileReader fileReader) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(fileReader);
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
}
