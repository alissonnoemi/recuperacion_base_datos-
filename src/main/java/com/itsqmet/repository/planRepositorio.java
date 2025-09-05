package com.itsqmet.repository;

import com.itsqmet.entity.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface planRepositorio extends MongoRepository<Plan, Long> {

}
