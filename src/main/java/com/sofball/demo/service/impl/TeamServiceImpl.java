package com.sofball.demo.service.impl;


import com.sofball.demo.dao.DivisionRepository;
import com.sofball.demo.dao.TeamRepository;
import com.sofball.demo.model.Team;
import com.sofball.demo.service.BaseService;
import com.sofball.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamServiceImpl extends BaseService implements TeamService {

    @Autowired
    private TeamRepository repository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Team> search(String filter, PageRequest pageRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Team> list(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public Team create(Team team) {
        team.setDivision(divisionRepository.findOne(team.getDivision().getId()));
        return repository.save(team);
    }

    @Override
    public String delete(Integer teamId) {
        Team team = repository.findOne(teamId);
        repository.delete();
        return team.getName();
    }

    @Override
    public Iterable<Team> all() {
        return repository.findAll();
    }
}
