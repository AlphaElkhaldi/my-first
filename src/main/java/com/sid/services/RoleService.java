package com.sid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sid.entities.AppRole;
import com.sid.repositories.AppRoleRepository;

@Service
public class RoleService {

    @Autowired
    private AppRoleRepository roleRepository;

    public AppRole getByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    public AppRole save(AppRole role) {
        return roleRepository.save(role);
    }

}