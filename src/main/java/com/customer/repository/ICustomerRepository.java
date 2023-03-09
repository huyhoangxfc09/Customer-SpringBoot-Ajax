package com.customer.repository;

import com.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select c from Customer as c where c.status = true ")
    List<Customer> findAllListCustomer();
    @Modifying
    @Query(value = "update Customer as  c set c.status = false where c.id = :id")
    void removeCustomer(@Param("id")Long id);
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findAllByNameContaining(String name,Pageable pageable);


//    @Query(value = "select c from Customer as c where c.name like :name")
//    Page<Customer> findByNamePag(@Param("name") String name,Pageable pageable);
}
