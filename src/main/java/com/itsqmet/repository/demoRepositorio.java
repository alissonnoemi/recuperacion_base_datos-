package com.itsqmet.repository;

import com.itsqmet.entity.Demo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface demoRepositorio extends MongoRepository<Demo , Long> {

}
