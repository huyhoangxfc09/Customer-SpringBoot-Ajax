package com.customer.service.core;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICrudService <E>{
    List<E> findAll();
    E save(MultipartFile file,E e);
    E findById(Long id);
    void remove(Long id);
}
