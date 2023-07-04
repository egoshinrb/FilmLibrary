package com.example.filmlibrary.source.repository;

import com.example.filmlibrary.source.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends GenericRepository<Director> {
    Page<Director> findAllByDirectorsFIOContainsIgnoreCaseAndDeletedIsFalse(String fio,
                                                                            Pageable pageable);

}