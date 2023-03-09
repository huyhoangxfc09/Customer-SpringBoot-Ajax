package com.customer.service.core;

import java.util.List;

public interface ICrudService <E>{
    List<E> findAll();
    E save(E e);
    E findById(Long id);
    void remove(Long id);
}
