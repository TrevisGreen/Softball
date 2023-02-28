package com.sofball.demo.service;

import com.sofball.demo.model.Season;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface SeasonService {

    public Page<Season> search(String filter, PageRequest pageRequest);

    public Page<Season> list(PageRequest pageRequest);

    public void create(Season season);

    public Season get(Integer seasonId);

    public void delete(Integer seasonId);

    public Iterable<Season> all();
}
