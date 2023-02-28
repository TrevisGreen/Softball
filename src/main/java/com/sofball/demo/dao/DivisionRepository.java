package com.sofball.demo.dao;

import com.sofball.demo.model.Division;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DivisionRepository extends PagingAndSortingRepository<Division, Integer> {

}
