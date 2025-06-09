package com.itsqmet.repository;

import com.itsqmet.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface demoRepositorio extends JpaRepository <Demo , Long> {

}
