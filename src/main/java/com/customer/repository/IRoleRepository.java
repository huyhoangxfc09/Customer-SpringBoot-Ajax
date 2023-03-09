package com.customer.repository;

import com.customer.model.Role;
import com.customer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
