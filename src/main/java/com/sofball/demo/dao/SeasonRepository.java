package com.sofball.demo.dao;

import com.sofball.demo.model.Season;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SeasonRepository extends PagingAndSortingRepository<Season, Integer> {

}
