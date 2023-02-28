package com.sofball.demo.service.impl;


import com.sofball.demo.dao.DivisionRepository;
import com.sofball.demo.model.Division;
import com.sofball.demo.service.BaseService;
import com.sofball.demo.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DivisionServiceImpl extends BaseService implements DivisionService {

    @Autowired
    private DivisionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<Division> search(String filter, PageRequest pageRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Division> list(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public Division create(Division division) {
        return repository.save(division);
    }

    @Override
    @Transactional(readOnly = true)
    public Division get(Integer divisionId) {
        return repository.findOne(divisionId);
    }

    @Override
    public String delete(Integer divisionId) {
        Division division = repository.findOne(divisionId);
        repository.delete(division);
        return division.getName();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Division> all() {
        return repository.findAll();
    }
}
