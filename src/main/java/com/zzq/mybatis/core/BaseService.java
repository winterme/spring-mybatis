package com.zzq.mybatis.core;

import java.util.List;

public interface BaseService<T> {

    int save(T t);

    T getById(Object id);

    List<T> getAll();

    List<T> getByEntity(T t);

}
