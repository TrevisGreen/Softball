package com.sofball.demo.dao;

import com.sofball.demo.model.Team;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team,Integer> {

}
