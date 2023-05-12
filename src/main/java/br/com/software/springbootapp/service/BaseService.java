package br.com.software.springbootapp.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, I> {

    Optional<T> findById(I id);

    T findByIdNotNull(I id);

    List<T> findAll();

    List<T> findAllById(Iterable<I> ids);

    T save(T entity);

    List<T> saveAll(Iterable<T> entities);

    void deleteAll(Iterable<? extends T> entities);

    void delete(T entity);
}
