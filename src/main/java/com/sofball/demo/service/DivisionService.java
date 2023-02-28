package com.sofball.demo.service;

import com.sofball.demo.model.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DivisionService {

    public Page<Division> search(String filter, PageRequest pageRequest);

    public Page<Division> list(PageRequest pageRequest);

    public Division create(Division division);

    public Division get(Integer divisionId);

    public String delete(Integer divisionId);

    public Iterable<Division> all();
}
