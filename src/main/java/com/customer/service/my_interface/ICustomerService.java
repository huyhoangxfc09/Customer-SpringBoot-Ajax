package com.customer.service.my_interface;

import com.customer.model.Customer;
import com.customer.service.core.ICrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends ICrudService<Customer> {
    Page<Customer> findAllPage(Pageable pageable);
    Page<Customer> findAllByNameContaining(String name,Pageable pageable);
}
