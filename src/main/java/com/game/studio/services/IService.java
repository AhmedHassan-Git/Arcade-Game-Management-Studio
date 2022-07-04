package com.game.studio.services;

import java.util.List;

public interface IService<T> {
    public T persist(T entity);

    public List<T> findAll();

    public T findById(long id);

    public T update(T entity, long id);

    public void delete(long id);

    //
    // public boolean deleteAll();
}