package com.sofball.demo.service.impl;


import com.sofball.demo.dao.TeamRepository;
import com.sofball.demo.dao.UserRepository;
import com.sofball.demo.model.User;
import com.sofball.demo.service.BaseService;
import com.sofball.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<User> search(String filter, PageRequest pageRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> list(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public User create(User user) {
        user.setTeam(teamRepository.findOne(user.getTeam().getId()));
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User get(Long userId) {
        return  repository.findOne(userId);
    }

    @Override
    public String delete(Long userId) {
        User user = repository.findOne(userId);
        repository.delete(user);
        return user.getFullName();
    }
}
