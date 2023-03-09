package com.customer.service.impl;

import com.customer.model.Role;
import com.customer.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository iRoleRepository;

    public Role findByName(String name) {
        return iRoleRepository.findByName(name);
    }
}
