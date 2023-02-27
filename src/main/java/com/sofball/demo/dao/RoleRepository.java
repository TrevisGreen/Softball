package com.sofball.demo.dao;

import com.sofball.demo.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, String> {

    Role findByAuthorityIgnoreCase(String authority);
}
