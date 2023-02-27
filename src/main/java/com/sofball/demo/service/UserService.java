package com.sofball.demo.service;

import com.sofball.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    public Page<User> search(String filter, PageRequest pageRequest);

    public Page<User> list(PageRequest pageRequest);

    public User create(User user);

    public User get(Long userId);

    public String delete(Long userId);
}
