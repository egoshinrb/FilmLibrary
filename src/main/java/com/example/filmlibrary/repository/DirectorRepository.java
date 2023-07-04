package com.example.filmlibrary.repository;

import com.example.filmlibrary.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository
        extends GenericRepository<Director>{

    Page<Director> findAllByDirectorsFIOContainsIgnoreCaseAndIsDeletedFalse(String fio,
                                                                       Pageable pageable);
}
