package com.sofball.demo.service;

import com.sofball.demo.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TeamService {

    public Page<Team> search(String filter, PageRequest pageRequest);

    public Page<Team> list(PageRequest pageRequest);

    public Team create(Team team);

    public Team get(Integer teamId);

    public String delete(Integer teamId);

    public Iterable<Team> all();
}
