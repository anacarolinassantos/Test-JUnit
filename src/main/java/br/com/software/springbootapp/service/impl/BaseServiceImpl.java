package br.com.software.springbootapp.service.impl;

import br.com.software.springbootapp.exceptions.RegistroNaoEncontradoException;
import br.com.software.springbootapp.helpper.Utils;
import br.com.software.springbootapp.repository.BaseRepository;
import br.com.software.springbootapp.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseServiceImpl<T, I, R extends BaseRepository<T, I>> implements BaseService<T, I> {

    @Autowired
    private ApplicationContext applicationContext;

    private R repository;

    protected BaseServiceImpl() {
        super();
    }

    @Override
    public Optional<T> findById(I id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        return getRepository().findById(id);
    }

    @Override
    public T findByIdNotNull(I id) {
        return findById(id).orElseThrow(() -> new RegistroNaoEncontradoException());
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findAllById(Iterable<I> ids) {
        return getRepository().findAllById(ids);
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    public void deleteAll(Iterable<? extends T> entities) {
        getRepository().deleteAll(entities);
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

    protected R getRepository() {
        if (repository == null) {
            repository = applicationContext.getBean(getClassOfR());
        }
        return repository;
    }

    private Class<R> getClassOfR() {
        return Utils.getClassOfType(getClass(), 2);
    }
}
