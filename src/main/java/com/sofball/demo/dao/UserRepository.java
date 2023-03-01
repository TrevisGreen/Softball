package com.sofball.demo.dao;


import com.sofball.demo.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);
}
