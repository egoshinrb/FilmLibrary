package com.example.filmlibrary.repository;

import com.example.filmlibrary.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends GenericRepository<Director> {

}
