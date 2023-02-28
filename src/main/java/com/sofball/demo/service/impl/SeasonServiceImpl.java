package com.sofball.demo.service.impl;

import com.sofball.demo.dao.SeasonRepository;
import com.sofball.demo.model.Season;
import com.sofball.demo.service.BaseService;
import com.sofball.demo.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public class SeasonServiceImpl extends BaseService implements SeasonService {

    @Autowired
    private SeasonRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<Season> search(String filter, PageRequest pageRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Season> list(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public void create(Season season) {
        repository.save(season);
    }

    @Override
    @Transactional(readOnly = true)
    public Season get(Integer seasonId) {
        return repository.findOne(seasonId);
    }

    @Override
    public void delete(Integer seasonId) {
        repository.delete(seasonId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Season> all() {
        return repository.findAll();
    }
}
